package com.sasakirione.pokebuild.model.user.resource

import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

object UserPokemons: IntIdTable("user_pokemons") {
    val userId = reference("user_id", Users)
    val pokemonId = reference("pokemon_id", Pokemons)
}