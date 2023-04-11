package com.sasakirione.pokebuild.plugins

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureHTTP() {
    install(CORS) {
        val allowHost = this@configureHTTP.environment.config.property("ktor.deployment.frontPath").getString()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader("*")
        allowHost(allowHost)
    }
    routing {
        openAPI(path = "openapi")
    }
}
