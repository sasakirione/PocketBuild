package com.sasakirione.pokebuild

import io.ktor.server.application.*
import com.sasakirione.pokebuild.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureRouting()
}
