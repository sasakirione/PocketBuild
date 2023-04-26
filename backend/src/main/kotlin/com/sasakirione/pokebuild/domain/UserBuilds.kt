package com.sasakirione.pokebuild.domain


/**
 * ユーザーの構築
 */
data class UserBuild(
    /**
     * 構築ID
     */
    val id: Int,
    /**
     * ユーザーID
     */
    val userId: Int,
    /**
     * 構築名
     */
    val name: String,
    /**
     * 構築の説明
     */
    val description: String,
    /**
     * シリーズ
     */
    val series: Int,
    /**
     * 世代
     */
    val generation: Int,
)

/**
 * ユーザーの構築に登録されているポケモンのリスト
 */
data class UserBuildPokemonList(
    /**
     * 構築ID
     */
    val buildId: Int,
    /**
     * ポケモンのリスト
     */
    val pokemonList: List<UserPokemon>
)
