package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Moves
import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

object PokemonMoves: IntIdTable("pokemon_moves") {
    val pokemonId = reference("pokemon_id", Pokemons)
    val moveId = reference("move_id", Moves)
    val generation = integer("generation")
}