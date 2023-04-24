package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.official.resource.Tags
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonTags: IntIdTable("user_pokemon_tags") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val tagId = reference("tag_id", Tags)
    val createdAt = datetime("created_at")
}