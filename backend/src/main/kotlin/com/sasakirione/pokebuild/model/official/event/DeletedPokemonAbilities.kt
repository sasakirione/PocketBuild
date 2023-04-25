package com.sasakirione.pokebuild.model.official.event

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンの特性の削除を記録するテーブル
 *　ポケモンの特性が削除された事を管理するテーブル
 *
 *  ## 使用例
 *  - 実装前に影踏みの特性が消されたシャンデラの場合は該当のマッピングテーブルのIDをpokemon_ability_idに指定した上で、実装前なのでgenerationには0を指定します。
 *　- 第6世代から第7世代でふゆうの特性が消されたゲンガーの場合はふゆうのマッピングテーブルのIDをpokemon_ability_idに指定した上で、generationには7を指定します。
 */
object DeletedPokemonAbilities: IntIdTable("deleted_pokemon_abilities") {
    /**
     * 該当のポケモンの特性のマッピングテーブルのID
     */
    val pokemonAbilityId = reference("pokemon_ability_id", PokemonAbilities)
    /**
     * 削除された世代(使えなくなった最初の世代、未解禁の場合は0を指定する)
     */
    val generation = integer("generation")
}