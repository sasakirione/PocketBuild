package com.sasakirione.pokebuild.controller

import com.sasakirione.pokebuild.domain.UserPokemon
import com.sasakirione.pokebuild.domain.UserPokemonValue
import com.sasakirione.pokebuild.repository.UserBuildRepository
import com.sasakirione.pokebuild.repository.UserPokemonRepository
import io.ktor.server.plugins.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserPokemonController: KoinComponent {
    private val pokemonRepo: UserPokemonRepository by inject()
    private val buildRepo: UserBuildRepository by inject()

    fun getUserPokemon(pokemonId: Int, userId: Int): UserPokemon {
        checkUser(pokemonId, userId)
        return pokemonRepo.getPokemon(pokemonId, userId) ?: throw NotFoundException()
    }

    fun getUserPokemonList(userId: Int): List<UserPokemon> = pokemonRepo.getPokemonList(userId)

    fun createUserPokemon(pokemon: UserPokemon, userId: Int): Int = pokemonRepo.createPokemon(pokemon, userId)

    fun updateAbility(pokemonId: Int, abilityId: Int, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateAbility(abilityId, pokemonId)
    }

    fun updateNature(pokemonId: Int, natureId: Int, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateNature(natureId, pokemonId)
    }

    fun updateTags(pokemonId: Int, tags: List<Int>, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateTags(tags, pokemonId)
    }

    fun updateMoves(pokemonId: Int, moves: List<Int>, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateMoves(moves, pokemonId)
    }

    fun updateGood(pokemonId: Int, goodId: Int, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateGood(goodId, pokemonId)
    }

    fun updateNickname(pokemonId: Int, nickname: String, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateNickname(nickname, pokemonId)
    }

    fun updateTerasType(pokemonId: Int, terasType: Int, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateTerasType(terasType, pokemonId)
    }

    fun updatevalue(pokemonId: Int, ev: UserPokemonValue, iv: UserPokemonValue, userId: Int) {
        checkUser(pokemonId, userId)
        pokemonRepo.updateValue(ev, iv, pokemonId)
    }

    fun updatePokemon(pokemon: UserPokemon, userId: Int) {
        checkUser(pokemon.id, userId)
        updatevalue(pokemon.id, pokemon.ev, pokemon.iv, userId)
        updateAbility(pokemon.id, pokemon.ability, userId)
        updateNature(pokemon.id, pokemon.nature, userId)
        updateTags(pokemon.id, pokemon.tags, userId)
        updateMoves(pokemon.id, pokemon.moves, userId)
        updateGood(pokemon.id, pokemon.good, userId)
        updateNickname(pokemon.id, pokemon.nickName, userId)
        updateTerasType(pokemon.id, pokemon.terasType, userId)
    }

    private fun checkUser(pokemonId: Int, userId: Int) {
        val isCorrect = pokemonRepo.isCreatedPokemon(pokemonId, userId)
        if (!isCorrect) {
            throw NotFoundException()
        }
    }
}