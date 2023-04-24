package com.sasakirione.pokebuild.model.official.event

import org.jetbrains.exposed.dao.id.IntIdTable

object DeletedPokemonAbilities: IntIdTable("deleted_pokemon_abilities") {
    val pokemonAbilityId = reference("pokemon_ability_id", PokemonAbilities)
    val generation = integer("generation")
}