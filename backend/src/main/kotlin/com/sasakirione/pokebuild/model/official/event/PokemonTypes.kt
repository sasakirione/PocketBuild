package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Pokemons
import com.sasakirione.pokebuild.model.official.resource.Types
import org.jetbrains.exposed.dao.id.IntIdTable

object PokemonTypes: IntIdTable("pokemon_types") {
    val pokemonId = reference("pokemon_id", Pokemons)
    val typeId = reference("type_id", Types)
    val generation = integer("generation")
}