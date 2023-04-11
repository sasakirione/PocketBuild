package com.sasakirione.pokebuild.plugins

import org.jetbrains.exposed.sql.*
import io.ktor.server.application.*

fun Application.configureDatabases() {
    val databaseUrl = environment.config.property("db.url").getString()
    val databaseUser = environment.config.property("db.user").getString()
    val databasePassword = environment.config.property("db.password").getString()

    Database.connect(
        url = databaseUrl,
        user = databaseUser,
        driver = "org.postgresql.Driver",
        password = databasePassword
    )
}
