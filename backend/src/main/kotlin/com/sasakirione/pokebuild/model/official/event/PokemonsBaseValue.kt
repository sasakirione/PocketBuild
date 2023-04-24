package com.sasakirione.pokebuild.model.official.event

import com.sasakirione.pokebuild.model.official.resource.Pokemons
import org.jetbrains.exposed.dao.id.IntIdTable

object PokemonsBaseValue: IntIdTable("pokemons_base_value") {
    val pokemonId = reference("pokemon_id", Pokemons)
    val baseHp = integer("base_hp")
    val baseA = integer("base_a")
    val baseB = integer("base_b")
    val baseC = integer("base_c")
    val baseD = integer("base_d")
    val baseS = integer("base_s")
    val generation = integer("generation")
}