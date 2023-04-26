package com.sasakirione.pokebuild

import io.ktor.server.application.*
import com.sasakirione.pokebuild.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    // DIの設定用コード
    configureDI()
    // 認証用コード
    configureSecurity()
    // CORSの設定等用コード
    configureHTTP()
    // JSONをいい感じにシリアライズするための設定用コード
    configureSerialization()
    // DBの設定とマイグレーション用コード
    configureDatabases()
    // ルーティング設定用コード
    configureRouting()
}
