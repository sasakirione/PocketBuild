package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Pokemons
import com.sasakirione.pokebuild.model.official.resource.Types
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンとタイプのマッピングテーブル
 * ポケモンのタイプを管理します。
 *
 * ## ルール
 * - 初期からあるタイプの場合はgenerationにポケモン自体の登場した世代を指定してください。
 * - 追加タイプの場合はそのタイプが追加された世代を指定してください。
 *
 * ## 使用例
 * - 第4世代でノーマルひこうとして追加され第6世代でフェアリーひこうに変更されたトゲキッスの場合は、ノーマルとひこうをgenerationに4を指定して登録し、フェアリーをgenerationに6を指定して登録します。
 */
object PokemonTypes: IntIdTable("pokemon_types") {
    /**
     * ポケモンID
     */
    val pokemonId = reference("pokemon_id", Pokemons)
    /**
     * タイプID
     */
    val typeId = reference("type_id", Types)
    /**
     * 世代(ポケモンが実装された世代、もしくはタイプが追加された世代)
     */
    val generation = integer("generation")
}