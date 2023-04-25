package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Abilities
import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンと特性のマッピングテーブル
 * ポケモンの特性を管理します。
 *
 * ## ルール
 * - 初期からある特性の場合はgenerationにポケモン自体の登場した世代を指定してください。
 * - 追加特性の場合はその特性が追加された世代を指定してください。
 */
object PokemonAbilities: IntIdTable("pokemon_abilities") {
    /**
     * ポケモンID
     */
    val pokemonId = reference("pokemon_id", Pokemons)
    /**
     * 特性ID
     */
    val abilityId = reference("ability_id", Abilities)
    /**
     * 夢特性かを表すフラグ(Trueの場合は夢特性、Falseの場合は通常特性)
     */
    val isHidden = bool("is_hidden")
    /**
     * 世代(ポケモンが実装された世代、もしくは特性が追加された世代)
     */
    val generation = integer("generation")
}