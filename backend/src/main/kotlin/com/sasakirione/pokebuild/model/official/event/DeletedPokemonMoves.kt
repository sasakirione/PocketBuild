package com.sasakirione.pokebuild.model.official.event

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンの技の削除を記録するテーブル
 * ポケモンの技の削除を記録するテーブル
 *
 * ## 使用例
 * - 第8世代から第9世代でねっとうが没収されたトリトドンの場合はねっとうとトリトドンのマッピングテーブルのIDをpokemon_move_idに指定した上で、generationには9を指定します。
 */
object DeletedPokemonMoves: IntIdTable("deleted_pokemon_moves") {
    /**
     * 該当のポケモンの技のマッピングテーブルのID
     */
    val pokemonMoveId = reference("pokemon_move_id", PokemonMoves)
    /**
     * 削除された世代(使えなくなった最初の世代、未解禁の場合は0を指定する)
     */
    val generation = integer("generation")
}