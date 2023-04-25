package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Moves
import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * # ポケモンと技のマッピングテーブル
 * ポケモンの技を管理します。
 *
 * ## ルール
 * - 初期からある技の場合はgenerationにポケモン自体の登場した世代を指定してください。
 * - 追加技の場合はその技が追加された世代を指定してください。
 */
object PokemonMoves: IntIdTable("pokemon_moves") {
    /**
     * ポケモンID
     */
    val pokemonId = reference("pokemon_id", Pokemons)
    /**
     * 技ID
     */
    val moveId = reference("move_id", Moves)
    /**
     * 世代(ポケモンが実装された世代、もしくは技が追加された世代)
     */
    val generation = integer("generation")
}