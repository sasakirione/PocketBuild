package com.sasakirione.pokebuild.plugins

import com.sasakirione.pokebuild.model.official.event.*
import com.sasakirione.pokebuild.model.official.resource.*
import com.sasakirione.pokebuild.model.user.resource.*
import com.sasakirione.pokebuild.model.user.event.*
import org.jetbrains.exposed.sql.*
import io.ktor.server.application.*

fun Application.configureDatabases() {
    // 設定ファイルの読み込み
    val databaseUrl = environment.config.property("db.url").getString()
    val databaseUser = environment.config.property("db.user").getString()
    val databasePassword = environment.config.property("db.password").getString()

    // DB接続
    Database.connect(
        url = databaseUrl,
        user = databaseUser,
        driver = "org.postgresql.Driver",
        password = databasePassword
    )

    // DBの作成
    SchemaUtils.createDatabase("pokebuild")
    // テーブルの作成
    SchemaUtils.createMissingTablesAndColumns(
        AbilityDetails,
        DeletedPokemonAbilities,
        DeletedPokemonMoves,
        DeletedPokemonTypes,
        GoodDetails,
        MoveDetails,
        PokemonAbilities,
        PokemonMoves,
        PokemonsBaseValue,
        PokemonTypes,
        Abilities,
        GoodCategories,
        Goods,
        MoveCategories,
        Moves,
        Natures,
        Pokemons,
        Tags,
        Types,
        DeletedUserBuilds,
        DeletedUserPokemons,
        UserBuildDetails,
        UserBuildPokemons,
        UserPokemonAbilities,
        UserPokemonGoods,
        UserPokemonMoves,
        UserPokemonNatures,
        UserPokemonNickNames,
        UserPokemonTags,
        UserPokemonTerasTypes,
        UserPokemonValues,
        UserBuilds,
        UserPokemons,
        Users
    )
}
