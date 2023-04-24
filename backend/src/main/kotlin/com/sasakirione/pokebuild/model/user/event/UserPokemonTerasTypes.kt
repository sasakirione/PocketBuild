package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.official.resource.Types
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonTerasTypes: IntIdTable("user_pokemon_tera_types") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val teraTypeId = reference("tera_type_id", Types)
    val createdAt = datetime("created_at")
}