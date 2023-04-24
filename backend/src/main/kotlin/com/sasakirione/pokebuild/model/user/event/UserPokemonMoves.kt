package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.official.resource.Moves
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonMoves: IntIdTable("user_pokemon_moves") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val moveId = reference("move_id", Moves)
    val createdAt = datetime("created_at")
}