package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.official.resource.Abilities
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonAbilities: IntIdTable("user_pokemon_abilities") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val abilityId = reference("ability_id", Abilities)
    val createdAt = datetime("created_at")
}