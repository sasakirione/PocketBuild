package com.sasakirione.pokebuild.controller

import com.sasakirione.pokebuild.domain.UserPokemon
import com.sasakirione.pokebuild.domain.UserPokemonValue
import com.sasakirione.pokebuild.repository.UserPokemonRepository
import com.sasakirione.pokebuild.repository.UserRepository
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserPokemonController : KoinComponent {
    private val pokemonRepo: UserPokemonRepository by inject()
    private val userRepo: UserRepository by inject()

    fun getUserPokemon(pokemonId: Int, authId: String): UserPokemon = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        return@transaction pokemonRepo.getPokemon(pokemonId, userId) ?: throw NotFoundException()
    }

    fun getUserPokemonList(authId: String): List<UserPokemon> = transaction {
        val userId = getUserId(authId)
        pokemonRepo.getPokemonList(userId)
    }

    fun createUserPokemon(pokemon: UserPokemon, authId: String): Int = transaction {
        val userId = getUserId(authId)
        pokemonRepo.createPokemon(pokemon, userId)
    }

    fun updateAbility(pokemonId: Int, abilityId: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateAbility(abilityId, pokemonId)
    }

    fun updateNature(pokemonId: Int, natureId: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateNature(natureId, pokemonId)
    }

    fun updateTags(pokemonId: Int, tags: List<Int>, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateTags(tags, pokemonId)
    }

    fun updateMoves(pokemonId: Int, moves: List<Int>, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateMoves(moves, pokemonId)
    }

    fun updateGood(pokemonId: Int, goodId: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateGood(goodId, pokemonId)
    }

    fun updateNickname(pokemonId: Int, nickname: String, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateNickname(nickname, pokemonId)
    }

    fun updateTerasType(pokemonId: Int, terasType: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateTerasType(terasType, pokemonId)
    }

    fun updateValue(pokemonId: Int, ev: UserPokemonValue, iv: UserPokemonValue, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemonId, userId)
        pokemonRepo.updateValue(ev, iv, pokemonId)
    }

    fun deletePokemon(id: Int, authId: String) {
        val userId = getUserId(authId)
        checkUser(id, userId)
        pokemonRepo.deletePokemon(id)
    }

    fun updatePokemon(pokemon: UserPokemon, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(pokemon.id, userId)
        pokemonRepo.updateValue(pokemon.ev, pokemon.iv, pokemon.pokemonId)
        pokemonRepo.updateAbility(pokemon.ability, pokemon.pokemonId)
        pokemonRepo.updateNature(pokemon.nature, pokemon.pokemonId)
        pokemonRepo.updateTags(pokemon.tags, pokemon.pokemonId)
        pokemonRepo.updateMoves(pokemon.moves, pokemon.pokemonId)
        pokemonRepo.updateGood(pokemon.good, pokemon.pokemonId)
        pokemonRepo.updateNickname(pokemon.nickName, pokemon.pokemonId)
        pokemonRepo.updateTerasType(pokemon.terasType, pokemon.pokemonId)
    }

    private fun getUserId(authId: String): Int = userRepo.getUserId(authId) ?: throw NotFoundException()

    private fun checkUser(pokemonId: Int, userId: Int) {
        val isCorrect = pokemonRepo.isCreatedPokemon(pokemonId, userId)
        if (!isCorrect) {
            throw NotFoundException()
        }
    }
}