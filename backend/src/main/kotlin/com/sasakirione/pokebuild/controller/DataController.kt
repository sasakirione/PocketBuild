package com.sasakirione.pokebuild.controller

import com.sasakirione.pokebuild.domain.UserPokemonValue
import com.sasakirione.pokebuild.repository.DataRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataController : KoinComponent {
    private val dataRepo: DataRepository by inject()

    fun getPokemonList() = transaction {
        dataRepo.getPokemonList()
    }

    fun getPokemon(id: Int, generation: Int) = transaction {
        dataRepo.getPokemon(id, generation)
    }

    fun getPokemonAbility(id: Int, generation: Int) = transaction {
        dataRepo.getAbility(id, generation)
    }

    fun getPokemonMoves(id: Int, generation: Int) = transaction {
        dataRepo.getMoveList(id, generation)
    }

    fun getGoodsList(generation: Int) = transaction {
        dataRepo.getGoodsList(generation)
    }

    fun getGoods(id: Int, generation: Int) = transaction {
        dataRepo.getGoods(id, generation)
    }

    fun getAbilityList(generation: Int) = transaction {
        dataRepo.getAbilityList(generation)
    }

    fun getAbility(id: Int, generation: Int) = transaction {
        dataRepo.getAbility2(id, generation)
    }

    fun getMoveList(generation: Int) = transaction {
        dataRepo.getAllMoveList(generation)
    }

    fun getMove(id: Int, generation: Int) = transaction {
        dataRepo.getMove(id, generation)
    }

    fun getTagList() = transaction {
        dataRepo.getTagList()
    }

    fun getNatureList() = transaction {
        dataRepo.getNatureList()
    }
}

data class Nature(
    val id: Int,
    val name: String,
    val up: String,
    val down: String
)

data class Tag(
    val id: Int,
    val name: String
)

data class Move(
    val id: Int,
    val name: String,
    val type: Int,
    val category: Int,
)

data class MoveDetail(
    val id: Int,
    val name: String,
    val type: Int,
    val category: Int,
    val description: String,
)


data class AbilityDetail(
    val id: Int,
    val name: String,
    val description: String
)

data class Ability(
    val id: Int,
    val name: String
)

data class Good(
    val id: Int,
    val name: String,
    val description: String,
    val category: Int
)

data class Pokemon(
    val id: Int,
    val dexNo: Int,
    val form: String,
    val formId: Int,
    val name: String,
)

data class PokemonDetail(
    val id: Int,
    val type: List<Int>,
    val ability: List<Int>,
    val bv: UserPokemonValue
)