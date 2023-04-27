package com.sasakirione.pokebuild.controller

import com.sasakirione.pokebuild.domain.UserBuild
import com.sasakirione.pokebuild.domain.UserBuildPokemonList
import com.sasakirione.pokebuild.repository.UserBuildRepository
import com.sasakirione.pokebuild.repository.UserPokemonRepository
import com.sasakirione.pokebuild.repository.UserRepository
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * ユーザーの構築に関するコントローラー
 */
class UserBuildController : KoinComponent {
    private val pokemonRepo: UserPokemonRepository by inject()
    private val buildRepo: UserBuildRepository by inject()
    private val userRepo: UserRepository by inject()

    fun getUserBuildList(authId: String) = transaction {
        val userId = getUserId(authId)
        val buildIdList = buildRepo.getBuildIdList(userId)
        buildIdList.mapNotNull { buildRepo.getBuild(it) }
    }

    fun createUserBuild(build: UserBuild) = transaction {
        buildRepo.createBuild(build)
    }

    fun updateUserBuild(build: UserBuild, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(build.id, userId)
        buildRepo.updateBuild(build)
    }

    fun deleteUserBuild(id: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(id, userId)
        buildRepo.deleteBuild(id)
    }

    fun getUserBuild(id: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(id, userId)
        buildRepo.getBuild(id) ?: throw NotFoundException()
    }

    fun getUserBuildPokemonList(id: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(id, userId)
        val pokemonIds = buildRepo.getPokemonList(id)
        val pokemonList = pokemonIds.mapNotNull { pokemonRepo.getPokemon(it, userId) }
        UserBuildPokemonList(id, pokemonList)
    }

    fun addUserBuildPokemon(buildId: Int, pokemonId: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(buildId, userId)
        checkUserPokemon(pokemonId, userId)
        buildRepo.addBuildPokemon(buildId, pokemonId)
    }

    fun deleteUserBuildPokemon(buildId: Int, pokemonId: Int, authId: String) = transaction {
        val userId = getUserId(authId)
        checkUser(buildId, userId)
        checkUserPokemon(pokemonId, userId)
        buildRepo.deleteBuildPokemon(buildId, pokemonId)
    }

    private fun checkUser(buildId: Int, userId: Int) {
        if (!buildRepo.isCorrectUserBuild(buildId, userId)) throw NotFoundException()
    }

    private fun checkUserPokemon(pokemonId: Int, userId: Int) {
        if (!pokemonRepo.isCreatedPokemon(pokemonId, userId)) throw NotFoundException()
    }

    private fun getUserId(authId: String): Int = userRepo.getUserId(authId) ?: throw NotFoundException()



}