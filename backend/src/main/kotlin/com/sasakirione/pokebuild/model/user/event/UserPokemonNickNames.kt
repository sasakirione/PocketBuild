package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable

object UserPokemonNickNames: IntIdTable("user_pokemon_nick_names") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val nickName = varchar("nick_name", 50)
}