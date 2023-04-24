package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Abilities
import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

object PokemonAbilities: IntIdTable("pokemon_abilities") {
    val pokemonId = reference("pokemon_id", Pokemons)
    val abilityId = reference("ability_id", Abilities)
    val isHidden = bool("is_hidden")
    val generation = integer("generation")
}