package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.official.resource.Natures
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonNatures: IntIdTable("user_pokemon_natures") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val natureId = reference("nature_id", Natures)
    val createdAt = datetime("created_at")
}