package com.sasakirione.pokebuild.plugins

import com.auth0.jwk.JwkProviderBuilder
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*
import java.util.concurrent.TimeUnit

fun Application.configureSecurity() {
    authentication {
        jwt {
            val jwtIssuer = this@configureSecurity.environment.config.property("auth0.issuer").getString()
            realm = "pocket-build-server"
            verifier(
                jwkProvider = JwkProviderBuilder(jwtIssuer)
                    .cached(10, 24, TimeUnit.HOURS)
                    .rateLimited(10, 1, TimeUnit.MINUTES)
                    .build(),
                jwtIssuer
            )
            validate { credential ->
                JWTPrincipal(credential.payload)
            }
        }
    }
}
