package com.sasakirione.pokebuild.model.official.event

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンのタイプの削除を記録するテーブル
 *　ポケモンのタイプが削除された事を管理するテーブル
 *
 * ## 使用例
 * - 第6世代でノーマルひこうからフェアリーひこうになったトゲキッスの場合、ノーマルとのマッピングテーブルのIDをpokemon_type_idに指定した上で、generationには6を指定します。
 *
 */
object DeletedPokemonTypes: IntIdTable("deleted_pokemon_types") {
    /**
     * 該当のポケモンのタイプのマッピングテーブルのID
     */
    val pokemonTypeId = reference("pokemon_type_id", PokemonTypes)
    /**
     * 削除された世代(使えなくなった最初の世代、未解禁の場合は0を指定する←これは該当なしと思われる)
     */
    val generation = integer("generation")
}