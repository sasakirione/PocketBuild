package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonValue: IntIdTable("user_pokemon_values") {
    val userPokemon = reference("user_pokemon_id", UserPokemons)
    val evH = integer("ev_h").default(0)
    val evA = integer("ev_a").default(0)
    val evB = integer("ev_b").default(0)
    val evC = integer("ev_c").default(0)
    val evD = integer("ev_d").default(0)
    val evS = integer("ev_s").default(0)
    val ivH = integer("iv_h").default(31)
    val ivA = integer("iv_a").default(31)
    val ivB = integer("iv_b").default(31)
    val ivC = integer("iv_c").default(31)
    val ivD = integer("iv_d").default(31)
    val ivS = integer("iv_s").default(31)
    val createdAt = datetime("created_at")
}