package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンの種族値を管理するテーブル
 * ポケモンの種族値を管理します。
 *
 * ## ルール
 * - 実装そのままの種族値の場合はgenerationにポケモン自体の登場した世代を指定してください。
 * - 変更後の種族値はその変更された世代を指定してください。
 */
object PokemonsBaseValue: IntIdTable("pokemons_base_value") {
    /**
     * ポケモンID
     */
    val pokemonId = reference("pokemon_id", Pokemons)
    /**
     * HP種族値
     */
    val baseHp = integer("base_hp")
    /**
     * 攻撃種族値
     */
    val baseA = integer("base_a")
    /**
     * 防御種族値
     */
    val baseB = integer("base_b")
    /**
     * 特攻種族値
     */
    val baseC = integer("base_c")
    /**
     * 特防種族値
     */
    val baseD = integer("base_d")
    /**
     * 素早さ種族値
     */
    val baseS = integer("base_s")
    /**
     * 世代(ポケモンが実装された世代、もしくは種族値が変更された世代)
     */
    val generation = integer("generation")
}