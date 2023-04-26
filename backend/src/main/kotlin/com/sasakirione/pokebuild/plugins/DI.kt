package com.sasakirione.pokebuild.plugins

import com.sasakirione.pokebuild.repository.UserBuildRepository
import com.sasakirione.pokebuild.repository.UserPokemonRepository
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureDI() {
    // DIの設定
    install(Koin) {
        modules(module)
    }
}

// DI対象のクラスを定義
val module = module {
    single { UserBuildRepository() }
    single { UserPokemonRepository() }
}