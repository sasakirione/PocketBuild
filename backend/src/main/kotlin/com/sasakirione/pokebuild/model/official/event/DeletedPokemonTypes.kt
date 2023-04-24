package com.sasakirione.pokebuild.model.official.event

import org.jetbrains.exposed.dao.id.IntIdTable

object DeletedPokemonTypes: IntIdTable("deleted_pokemon_types") {
    val pokemonTypeId = reference("pokemon_type_id", PokemonTypes)
    val generation = integer("generation")
}