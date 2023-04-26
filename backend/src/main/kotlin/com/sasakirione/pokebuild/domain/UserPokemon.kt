package com.sasakirione.pokebuild.domain

/**
 * ユーザーのポケモン
 */
data class UserPokemon(
    /**
     * ポケモンID
     */
    val id: Int,
    /**
     * ユーザーID
     */
    val userId: Int,
    /**
     * ポケモンの種族ID
     */
    val pokemonId: Int,
    /**
     * ポケモンの努力値
     */
    val ev: UserPokemonValue,
    /**
     * ポケモンの個体値
     */
    val iv: UserPokemonValue,
    /**
     * ポケモンの性格
     */
    val nature: Int,
    /**
     * ポケモンの特性
     */
    val ability: Int,
    /**
     * ポケモンの技
     */
    val moves: List<Int>,
    /**
     * ポケモンのタグ
     */
    val tags: List<Int>,
    /**
     * ポケモンの道具
     */
    val good: Int,
    /**
     * ポケモンのニックネーム
     */
    val nickName: String,
    /**
     * ポケモンのテラスタイプ
     */
    val terasType: Int
)

/**
 * ユーザーのポケモンの値
 */
data class UserPokemonValue(
    /**
     * HP
     */
    val h: Int,
    /**
     * 攻撃
     */
    val a: Int,
    /**
     * 防御
     */
    val b: Int,
    /**
     * 特攻
     */
    val c: Int,
    /**
     * 特防
     */
    val d: Int,
    /**
     * 素早さ
     */
    val s: Int
)