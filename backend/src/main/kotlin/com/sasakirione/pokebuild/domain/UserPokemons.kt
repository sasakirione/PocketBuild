package com.sasakirione.pokebuild.domain

data class UserPokemon(
    val id: Int,
    val userId: Int,
    val pokemonId: Int,
    val ev: UserPokemonValue,
    val iv: UserPokemonValue,
    val nature: Int,
    val ability: Int,
    val moves: List<Int>,
    val tags: List<Int>,
    val good: Int,
    val nickName: String,
    val terasType: Int
)

data class UserPokemonValue(
    val h: Int,
    val a: Int,
    val b: Int,
    val c: Int,
    val d: Int,
    val s: Int
)