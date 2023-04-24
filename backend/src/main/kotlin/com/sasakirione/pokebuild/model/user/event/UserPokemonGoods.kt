package com.sasakirione.pokebuild.model.user.event

import com.sasakirione.pokebuild.model.official.resource.Goods
import com.sasakirione.pokebuild.model.user.resource.UserPokemons
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserPokemonGoods: IntIdTable("user_pokemon_goods") {
    val userPokemonId = reference("user_pokemon_id", UserPokemons)
    val goodId = reference("good_id", Goods)
    val createdAt = datetime("created_at")
}