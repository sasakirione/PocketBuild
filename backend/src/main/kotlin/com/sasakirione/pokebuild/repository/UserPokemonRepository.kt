package com.sasakirione.pokebuild.repository

import com.sasakirione.pokebuild.domain.UserPokemon
import com.sasakirione.pokebuild.domain.UserPokemonValue
import com.sasakirione.pokebuild.model.user.event.*
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * ユーザーのポケモンに関するリポジトリ
 */
class UserPokemonRepository {
    /**
     * ユーザーのポケモンの特性を変更する
     *
     * @param abilityId 変更する特性のID
     * @param pokemonId 変更するポケモンのID
     */
    fun updateAbility(abilityId: Int, pokemonId: Int) {
        UserPokemonAbilities.insert {
            it[UserPokemonAbilities.abilityId] = abilityId
            it[userPokemonId] = pokemonId
            it[createdAt] = LocalDateTime.now(ZoneOffset.UTC)
        }
    }

    /**
     * ユーザーのポケモンの性格を変更する
     *
     * @param natureId 変更する性格のID
     * @param pokemonId 変更するポケモンのID
     */
    fun updateNature(natureId: Int, pokemonId: Int) {
        UserPokemonNatures.insert {
            it[UserPokemonNatures.natureId] = natureId
            it[userPokemonId] = pokemonId
            it[createdAt] = LocalDateTime.now(ZoneOffset.UTC)
        }
    }

    /**
     * ユーザーのポケモンのタグを変更する
     *
     * @param tags 変更するタグのリスト
     * @param pokemonId 変更するポケモンのID
     */
    fun updateTags(tags: List<Int>, pokemonId: Int) {
        val createdAt = LocalDateTime.now(ZoneOffset.UTC)
        tags.forEach { tag ->
            UserPokemonTags.insert {
                it[tagId] = tag
                it[userPokemonId] = pokemonId
                it[UserPokemonTags.createdAt] = createdAt
            }
        }
    }

    /**
     * ユーザーのポケモンの技を変更する
     *
     * @param moves 変更する技のリスト
     * @param pokemonId 変更するポケモンのID
     */
    fun updateMoves(moves: List<Int>, pokemonId: Int) {
        val createdAt = LocalDateTime.now(ZoneOffset.UTC)
        moves.forEach { move ->
            UserPokemonMoves.insert {
                it[moveId] = move
                it[userPokemonId] = pokemonId
                it[UserPokemonMoves.createdAt] = createdAt
            }
        }
    }

    /**
     * ユーザーのポケモンの道具を変更する
     *
     * @param goodId 変更する道具
     * @param pokemonId 変更するポケモンのID
     */
    fun updateGood(goodId: Int, pokemonId: Int) {
        UserPokemonGoods.insert {
            it[UserPokemonGoods.goodId] = goodId
            it[userPokemonId] = pokemonId
            it[UserPokemonTags.createdAt] = LocalDateTime.now(ZoneOffset.UTC)
        }
    }

    /**
     * ユーザーのポケモンを取得する
     *
     * @param pokemonId 取得するポケモンのID
     * @param userId 取得するポケモンのユーザーID
     * @return ユーザーのポケモン
     */
    fun getPokemon(pokemonId: Int, userId: Int): UserPokemon? {
        val isDeleted = DeletedUserPokemons.select { DeletedUserPokemons.id eq pokemonId }.count() != 0L
        if (isDeleted) {
            return null
        }
        val pokemon = UserPokemons.select { UserPokemons.id eq pokemonId }.firstOrNull() ?: return null
        val pokemonSeedId = pokemon[UserPokemons.pokemonId]
        if (pokemon[UserPokemons.userId].value != userId) {
            return null
        }
        val abilityId = UserPokemonAbilities.select { UserPokemonAbilities.userPokemonId eq pokemonId }
            .orderBy(UserPokemonAbilities.createdAt, SortOrder.DESC)
            .first()[UserPokemonAbilities.abilityId]
        val natureId = UserPokemonNatures.select { UserPokemonNatures.userPokemonId eq pokemonId }
            .orderBy(UserPokemonNatures.createdAt, SortOrder.DESC)
            .first()[UserPokemonNatures.natureId]
        val tagMax = UserPokemonTags.select { UserPokemonTags.userPokemonId eq pokemonId }
            .orderBy(UserPokemonTags.createdAt, SortOrder.DESC)
            .first()[UserPokemonTags.createdAt]
        val tagIds =
            UserPokemonTags.select { (UserPokemonTags.userPokemonId eq pokemonId).and(UserPokemonTags.createdAt eq tagMax) }
                .map { it[UserPokemonTags.tagId] }
        val moveMax = UserPokemonMoves.select { UserPokemonMoves.userPokemonId eq pokemonId }
            .orderBy(UserPokemonMoves.createdAt, SortOrder.DESC)
            .first()[UserPokemonMoves.createdAt]
        val moveIds =
            UserPokemonMoves.select { (UserPokemonMoves.userPokemonId eq pokemonId).and(UserPokemonMoves.createdAt eq moveMax) }
                .map { it[UserPokemonMoves.moveId] }
        val goodId = UserPokemonGoods.select { UserPokemonGoods.userPokemonId eq pokemonId }
            .orderBy(UserPokemonGoods.createdAt, SortOrder.DESC)
            .first()[UserPokemonGoods.goodId]
        val nickName = UserPokemonNickNames.select { UserPokemonNickNames.userPokemonId eq pokemonId }
            .orderBy(UserPokemonNickNames.createdAt, SortOrder.DESC)
            .first()[UserPokemonNickNames.nickName]
        val terasType = UserPokemonTerasTypes.select { UserPokemonTerasTypes.userPokemonId eq pokemonId }
            .orderBy(UserPokemonTerasTypes.createdAt, SortOrder.DESC)
            .first()[UserPokemonTerasTypes.teraTypeId]
        val value = UserPokemonValues.select { UserPokemonValues.userPokemonId eq pokemonId }
            .orderBy(UserPokemonValues.createdAt, SortOrder.DESC)
            .first()
        val ev = UserPokemonValue(
            value[UserPokemonValues.evH],
            value[UserPokemonValues.evA],
            value[UserPokemonValues.evB],
            value[UserPokemonValues.evC],
            value[UserPokemonValues.evD],
            value[UserPokemonValues.evS]
        )
        val iv = UserPokemonValue(
            value[UserPokemonValues.ivH],
            value[UserPokemonValues.ivA],
            value[UserPokemonValues.ivB],
            value[UserPokemonValues.ivC],
            value[UserPokemonValues.ivD],
            value[UserPokemonValues.ivS]
        )
        return UserPokemon(
            pokemonId,
            userId,
            pokemonSeedId.value,
            ev,
            iv,
            natureId.value,
            abilityId.value,
            moveIds.map { it.value },
            tagIds.map { it.value },
            goodId.value,
            nickName,
            terasType.value
        )
    }

    /**
     * ユーザーのポケモンを作成する
     *
     * @param pokemon 作成するポケモン
     * @param userId 作成するポケモンのユーザーID
     * @return 作成したポケモンのID
     */
    fun createPokemon(pokemon: UserPokemon, userId: Int): Int {
        val pokemonId = UserPokemons.insert {
            it[UserPokemons.userId] = userId
            it[UserPokemons.pokemonId] = pokemon.pokemonId
        } get UserPokemons.id
        val createdAt = LocalDateTime.now(ZoneOffset.UTC)
        UserPokemonValues.insert {
            it[userPokemonId] = pokemonId
            it[evH] = pokemon.ev.h
            it[evA] = pokemon.ev.a
            it[evB] = pokemon.ev.d
            it[evC] = pokemon.ev.c
            it[evD] = pokemon.ev.d
            it[evS] = pokemon.ev.s
            it[ivH] = pokemon.iv.h
            it[ivA] = pokemon.iv.a
            it[ivB] = pokemon.iv.d
            it[ivC] = pokemon.iv.c
            it[ivD] = pokemon.iv.d
            it[ivS] = pokemon.iv.s
            it[UserPokemonValues.createdAt] = createdAt
        }
        UserPokemonAbilities.insert {
            it[userPokemonId] = pokemonId
            it[abilityId] = pokemon.ability
            it[UserPokemonAbilities.createdAt] = createdAt
        }
        UserPokemonNatures.insert {
            it[userPokemonId] = pokemonId
            it[natureId] = pokemon.nature
            it[UserPokemonNatures.createdAt] = createdAt
        }
        pokemon.tags.forEach { tag ->
            UserPokemonTags.insert {
                it[userPokemonId] = pokemonId
                it[tagId] = tag
                it[UserPokemonTags.createdAt] = createdAt
            }
        }
        pokemon.moves.forEach { move ->
            UserPokemonMoves.insert {
                it[userPokemonId] = pokemonId
                it[moveId] = move
                it[UserPokemonMoves.createdAt] = createdAt
            }
        }
        UserPokemonGoods.insert {
            it[userPokemonId] = pokemonId
            it[goodId] = pokemon.good
            it[UserPokemonTags.createdAt] = createdAt
        }
        UserPokemonNickNames.insert {
            it[userPokemonId] = pokemonId
            it[nickName] = pokemon.nickName
            it[UserPokemonNickNames.createdAt] = createdAt
        }
        UserPokemonTerasTypes.insert {
            it[userPokemonId] = pokemonId
            it[teraTypeId] = pokemon.terasType
            it[UserPokemonTerasTypes.createdAt] = createdAt
        }
        return pokemonId.value
    }
}