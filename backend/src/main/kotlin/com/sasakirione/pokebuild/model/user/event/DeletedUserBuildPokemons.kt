package com.sasakirione.pokebuild.model.user.event

import org.jetbrains.exposed.dao.id.IntIdTable

object DeletedUserBuildPokemons: IntIdTable("deleted_user_build_pokemons") {
    val userBuildPokemon = reference("user_build_pokemon_id", UserBuildPokemons)
}