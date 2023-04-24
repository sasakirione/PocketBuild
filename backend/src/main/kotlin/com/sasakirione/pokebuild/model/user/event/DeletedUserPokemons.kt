package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable

object DeletedUserPokemons: IntIdTable("deleted_user_pokemons") {
    val userPokemons = reference("user_pokemons", UserPokemons)
}