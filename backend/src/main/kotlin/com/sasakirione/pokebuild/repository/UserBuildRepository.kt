package com.sasakirione.pokebuild.repository

import com.sasakirione.pokebuild.domain.UserBuild
import com.sasakirione.pokebuild.model.user.event.DeletedUserBuildPokemons
import com.sasakirione.pokebuild.model.user.event.DeletedUserBuilds
import com.sasakirione.pokebuild.model.user.event.UserBuildDetails
import com.sasakirione.pokebuild.model.user.event.UserBuildPokemons
import com.sasakirione.pokebuild.model.user.resource.UserBuilds
import org.jetbrains.exposed.sql.*
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * ユーザーの構築に関するリポジトリ
 */
class UserBuildRepository {
    /**
     * 構築を作成する
     *
     * @param build 構築
     * @return 作成した構築のID
     */
    fun createBuild(build: UserBuild): Int {
        // 構築を作成する
        val buildId = UserBuilds.insert {
            it[userId] = build.userId
        } get UserBuilds.id

        // 構築の詳細を作成する
        UserBuildDetails.insert {
            it[userBuildId] = buildId.value
            it[buildName] = build.name
            it[buildDetail] = build.description
            it[series] = build.series
            it[generation] = build.generation
            it[createdAt] = LocalDateTime.now(ZoneOffset.UTC)
        }

        return buildId.value
    }

    /**
     * 構築情報を更新する
     *
     * @param build 構築
     */
    fun updateBuild(build: UserBuild) {
        UserBuildDetails.update({ UserBuildDetails.userBuildId eq build.id }) {
            it[buildName] = build.name
            it[buildDetail] = build.description
            it[series] = build.series
            it[generation] = build.generation
        }
    }

    /**
     * 構築を取得する
     *
     * @param buildId 構築ID
     * @return 構築(存在しない場合はnull)
     */
    fun getBuild(buildId: Int): UserBuild? {
        // 削除済みの構築かどうかを確認する
        val isDeleted = DeletedUserBuilds.select { DeletedUserBuilds.id eq buildId }.count() > 0
        if (isDeleted) {
            return null
        }

        // 構築情報を取得する
        val build = UserBuilds.select { UserBuilds.id eq buildId }.firstOrNull() ?: return null
        val buildDetails = UserBuildDetails.select { UserBuildDetails.userBuildId eq buildId }
            .orderBy(UserBuildDetails.createdAt, SortOrder.DESC).firstOrNull() ?: return null

        return UserBuild(
            build[UserBuilds.id].value,
            build[UserBuilds.userId].value,
            buildDetails[UserBuildDetails.buildName],
            buildDetails[UserBuildDetails.buildDetail],
            buildDetails[UserBuildDetails.series],
            buildDetails[UserBuildDetails.generation]
        )
    }

    /**
     * 構築にポケモンを追加する
     *
     * @param buildId 構築ID
     * @param pokemonId ポケモンID
     */
    fun addBuildPokemon(buildId: Int, pokemonId: Int) {
        UserBuildPokemons.insert {
            it[userBuildId] = buildId
            it[userPokemonId] = pokemonId
        }
    }

    /**
     * 構築に登録されているポケモンのIDリストを取得する
     *
     * @param buildId 構築ID
     * @return ポケモンIDリスト
     */
    fun getPokemonList(buildId: Int): List<Int> {
        // 削除済みも含めて全てのポケモンを取得する
        val pokemonList =  UserBuildPokemons.select { UserBuildPokemons.userBuildId eq buildId }
        val idList = pokemonList.map { it[UserBuildPokemons.id] }

        // 削除済みのポケモンを取得する
        val deletedIdList = DeletedUserBuildPokemons.select { DeletedUserBuildPokemons.userBuildPokemon inList idList }
            .map { it[DeletedUserBuildPokemons.userBuildPokemon] }

        // 削除済みのポケモンを除いたポケモンIDリストを返す
        return pokemonList.filter { it[UserBuildPokemons.id] !in deletedIdList }.map { it[UserBuildPokemons.userPokemonId].value }
    }

    /**
     * 構築を削除する
     *
     * @param buildId 構築ID
     */
    fun deleteBuild(buildId: Int) {
        val userBuildId = UserBuilds.select { UserBuilds.id eq buildId }.firstOrNull()?.get(UserBuilds.id) ?: return
        DeletedUserBuilds.insert {
            it[userBuilds] = userBuildId
        }
    }

    /**
     * 構築からポケモンを削除する
     *
     * @param buildId 構築ID
     * @param pokemonId ポケモンID
     */
    fun deleteBuildPokemon(buildId: Int, pokemonId: Int) {
        val userBuildPokemonId = UserBuildPokemons.select { UserBuildPokemons.userBuildId eq buildId }
            .andWhere { UserBuildPokemons.userPokemonId eq pokemonId }.firstOrNull()?.get(UserBuildPokemons.id) ?: return
        DeletedUserBuildPokemons.insert {
            it[userBuildPokemon] = userBuildPokemonId
        }
    }

    /**
     * 対象の構築のユーザーが正しいか
     *
     * @param buildId 構築ID
     * @param userId ユーザーID
     *
     * @return そのユーザーの構築の場合はTrue、構築が存在しないか他のユーザーのものの場合はFalse
     */
    fun isCorrectUserBuild(buildId: Int, userId: Int): Boolean {
        val build = UserBuilds.select { UserBuilds.id eq buildId }.firstOrNull() ?: return false
        return build[UserBuilds.userId].value == userId
    }

    /**
     * ユーザーの構築IDリストを取得する
     *
     * @param userId ユーザーID
     * @return 構築IDリスト
     */
    fun getBuildIdList(userId: Int): List<Int> {
        // 削除済みも含めて全ての構築を取得する
        val buildIdList = UserBuilds.select { UserBuilds.userId eq userId }.map { it[UserBuilds.id].value }

        // 削除済みの構築を取得する
        val deletedBuildIdList = DeletedUserBuilds.select { DeletedUserBuilds.userBuilds inList buildIdList }
            .map { it[DeletedUserBuilds.userBuilds].value }

        // 削除済みの構築を除いた構築IDリストを返す
        return buildIdList.filter { it !in deletedBuildIdList }
    }
}