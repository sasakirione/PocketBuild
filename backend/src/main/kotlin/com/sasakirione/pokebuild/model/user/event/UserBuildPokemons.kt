package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.user.resource.UserBuilds
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserBuildPokemons: IntIdTable("user_build_pokemons") {
    val userBuildId = reference("user_build_id", UserBuilds)
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val createdAt = datetime("created_at")
}