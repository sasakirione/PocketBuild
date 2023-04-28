package com.sasakirione.pokebuild.repository

import com.sasakirione.pokebuild.controller.*
import com.sasakirione.pokebuild.domain.UserPokemonValue
import com.sasakirione.pokebuild.model.official.event.*
import com.sasakirione.pokebuild.model.official.resource.*
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.*

/**
 * ポケモンのデータに関するリポジトリ
 */
class DataRepository {
    /**
     * ポケモンの一覧を取得する
     *
     * @return ポケモンの一覧
     */
    fun getPokemonList(): List<Pokemon> {
        return Pokemons.selectAll().map {
            Pokemon(
                it[Pokemons.id].value,
                it[Pokemons.dexNo],
                it[Pokemons.formName] ?: "",
                it[Pokemons.formNo],
                it[Pokemons.name],
            )
        }
    }

    /**
     * ポケモンの詳細を取得する
     *
     * @param id ポケモンのID
     * @param generation 世代
     * @return ポケモンの詳細
     */
    fun getPokemon(id: Int, generation: Int): PokemonDetail {
        // 削除済みのものを含めて全てのタイプを取得する
        val typeIds = PokemonTypes.select {
            PokemonTypes.pokemonId eq id and (PokemonTypes.generation lessEq generation)
        }.map {
            it[PokemonTypes.typeId]
        }
        // 削除済みのタイプを取得する
        val deletedTypeIds = PokemonTypes.select {
            PokemonTypes.pokemonId eq id and (PokemonTypes.generation lessEq generation)
        }.map {
            it[PokemonTypes.typeId]
        }
        // 削除済みのタイプを除外する
        val types = typeIds.filterNot { deletedTypeIds.contains(it) }.map { it.value }

        val abilities = getAbility(id, generation)

        val bv = PokemonsBaseValue.select {
            PokemonsBaseValue.pokemonId eq id and (PokemonsBaseValue.generation lessEq generation)
        }.map {
            UserPokemonValue(
                it[PokemonsBaseValue.baseHp],
                it[PokemonsBaseValue.baseA],
                it[PokemonsBaseValue.baseB],
                it[PokemonsBaseValue.baseC],
                it[PokemonsBaseValue.baseD],
                it[PokemonsBaseValue.baseS],
            )
        }.maxByOrNull { PokemonsBaseValue.generation } ?: throw Exception("指定された世代にポケモンの種族値が存在しません")

        return PokemonDetail(
            id,
            types,
            abilities,
            bv,
        )
    }

    /**
     * ポケモンの特性を取得する
     *
     * @param id ポケモンのID
     * @param generation 世代
     * @return ポケモンの特性
     */
    fun getAbility(id: Int, generation: Int): List<Int> {
        // 削除済みのものを含めて全ての特性を取得する
        val abilities = PokemonAbilities.select {
            PokemonAbilities.pokemonId eq id and (PokemonAbilities.generation lessEq generation)
        }
        val abilityIds = abilities.map {
            it[PokemonAbilities.abilityId]
        }

        // 削除済みの特性を取得する
        val deletedAbilityIds = DeletedPokemonAbilities.select {
            DeletedPokemonAbilities.pokemonAbilityId inList(abilityIds) and (DeletedPokemonAbilities.generation lessEq generation)
        }.map {
            it[DeletedPokemonAbilities.pokemonAbilityId]
        }

        // 削除済みの特性を除外する
        return abilities.filterNot { deletedAbilityIds.contains(it[PokemonAbilities.id]) }.map { it[PokemonAbilities.abilityId].value }
    }

    /**
     * ポケモンの技を取得する
     *
     * @param id ポケモンのID
     * @param generation 世代
     * @return ポケモンの技
     */
    fun getMoveList(id: Int, generation: Int): List<Int> {
        // 削除済みのものを含めて全ての技を取得する
        val moves = PokemonMoves.select {
            PokemonMoves.pokemonId eq id and (PokemonMoves.generation lessEq generation)
        }
        val moveIds = moves.map {
            it[PokemonMoves.moveId]
        }

        // 技とポケモンのマッピングは登録がない場合があるのでその場合はこちらを返す
        if (moveIds.isEmpty()) {
             throw NotFoundException("指定されたポケモンの技が存在しません")
        }

        // 削除済みの技を取得する
        val deletedMoveIds = DeletedPokemonMoves.select {
            DeletedPokemonMoves.pokemonMoveId  inList(moveIds)  and (PokemonAbilities.generation lessEq generation)
        }.map {
            it[DeletedPokemonMoves.pokemonMoveId]
        }

        // 削除済みの技を除外する
        return moves.filterNot { deletedMoveIds.contains(it[PokemonMoves.id]) }.map { it[PokemonMoves.moveId].value }
    }

    /**
     * 道具一覧を取得する
     *
     * @param generation 世代
     * @return 道具一覧
     */
    fun getGoodsList(generation: Int): List<Good> {
        // 道具の詳細情報を取得して指定の世代より前で最新のものを一つずつ選ぶ
        return Goods.innerJoin(GoodDetails).select {
            GoodDetails.generation lessEq generation
        }.map {
            Good(
                it[Goods.id].value,
                it[Goods.name],
                it[GoodDetails.description],
                it[GoodDetails.category].value,
            ) to it[GoodDetails.generation]
        }.groupBy { it.first.id }.mapNotNull { it.value.maxByOrNull { it2 -> it2.second } }.map { it.first }

    }

    /**
     * 道具を取得する
     *
     * @param id 道具のID
     * @param generation 世代
     * @return 道具
     */
    fun getGoods(id: Int, generation: Int): Good {
        // 道具の詳細情報を取得して指定の世代より前で最新のものを一つずつ選ぶ
        return Goods.innerJoin(GoodDetails).select {
            Goods.id eq id and (GoodDetails.generation lessEq generation)
        }.orderBy(GoodDetails.generation, SortOrder.DESC).map {
            Good(
                it[Goods.id].value,
                it[Goods.name],
                it[GoodDetails.description],
                it[GoodDetails.category].value,
            )
        }.firstOrNull() ?: throw NotFoundException("指定された道具が存在しません")
    }

    /**
     * 特性一覧を取得する
     *
     * @param generation 世代
     * @return 特性一覧
     */
    fun getAbilityList(generation: Int): List<Ability> {
        return Abilities.select {
            Abilities.generation lessEq generation
        }.map {
            Ability(
                it[Abilities.id].value,
                it[Abilities.name],
            )
        }
    }

    /**
     * 特性を取得する
     *
     * @param id 特性のID
     * @param generation 世代
     * @return 特性
     */
    fun getAbility2(id: Int, generation: Int): AbilityDetail {
        return Abilities.innerJoin(AbilityDetails).select {
            Abilities.id eq id and (Abilities.generation lessEq generation)
        }.orderBy(Abilities.generation, SortOrder.DESC).map {
            AbilityDetail(
                it[Abilities.id].value,
                it[Abilities.name],
                it[AbilityDetails.description],
            )
        }.firstOrNull() ?: throw NotFoundException("指定された特性が存在しません")
    }

    /**
     * 技一覧を取得する
     *
     * @param generation 世代
     * @return 技一覧
     */
    fun getAllMoveList(generation: Int): List<Move> {
        return Moves.select {
            Moves.generation lessEq generation
        }.map {
            Move(
                it[Moves.id].value,
                it[Moves.name],
                it[Moves.type].value,
                it[Moves.category].value,
            )
        }
    }

    /**
     * 技を取得する
     *
     * @param id 技のID
     * @param generation 世代
     * @return 技
     */
    fun getMove(id: Int, generation: Int): MoveDetail {
        return Moves.innerJoin(MoveDetails).select {
            Moves.id eq id and (Moves.generation lessEq generation)
        }.orderBy(Moves.generation, SortOrder.DESC).map {
            MoveDetail(
                it[Moves.id].value,
                it[Moves.name],
                it[Moves.type].value,
                it[Moves.category].value,
                it[MoveDetails.description],
            )
        }.firstOrNull() ?: throw NotFoundException("指定された技が存在しません")
    }

    /**
     * タグ一覧を取得する
     *
     * @return タグ一覧
     */
    fun getTagList(): List<Tag> {
        return Tags.selectAll().map {
            Tag(
                it[Tags.id].value,
                it[Tags.name],
            )
        }
    }

    fun getNatureList(): List<Nature> {
        return Natures.selectAll().map {
            Nature(
                it[Natures.id].value,
                it[Natures.name],
                it[Natures.increase],
                it[Natures.decrease],
            )
        }
    }

}