package com.sasakirione.pokebuild.domain

import com.fasterxml.jackson.databind.ObjectWriter.GeneratorSettings

data class UserBuild(
    val id: Int,
    val userId: Int,
    val name: String,
    val description: String,
    val series: Int,
    val generation: Int,
)

data class UserBuildPokemonList(
    val buildId: Int,
    val pokemonList: List<UserPokemon>
)
