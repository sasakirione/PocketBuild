package com.sasakirione.pokebuild.model.official.event

import org.jetbrains.exposed.dao.id.IntIdTable

object DeletedPokemonMoves: IntIdTable("deleted_pokemon_moves") {
    val pokemonMoveId = reference("pokemon_move_id", PokemonMoves)
    val generation = integer("generation")
}