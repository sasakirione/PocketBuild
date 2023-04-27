package com.sasakirione.pokebuild.plugins

import com.sasakirione.pokebuild.controller.UserPokemonController
import com.sasakirione.pokebuild.domain.UserPokemon
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        route("/v1") {
            authenticate {
                route("pokemon") {
                    pokemonRouting()
                }
                route("build") {
                    buildRouting()
                }
            }
        }
    }
}

fun Route.pokemonRouting() {
    val pokemonController: UserPokemonController by inject()

    get {
        val authId = getAuthId()
        pokemonController.getUserPokemonList(authId)
    }

    post {
        val authId = getAuthId()
        val pokemon = call.receive<UserPokemon>()
        pokemonController.createUserPokemon(pokemon, authId)
    }

    route("{id}") {
        get {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            pokemonController.getUserPokemon(id, authId)
        }

        put {
            val authId = getAuthId()
            val pokemon = call.receive<UserPokemon>()
            pokemonController.updatePokemon(pokemon, authId)
        }

        delete {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            pokemonController.deletePokemon(id, authId)
        }

        put("ability") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val abilityId = call.receive<Int>()
            pokemonController.updateAbility(id, abilityId, authId)
        }

        put("nature") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val natureId = call.receive<Int>()
            pokemonController.updateNature(id, natureId, authId)
        }

        put("good") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val goodId = call.receive<Int>()
            pokemonController.updateGood(id, goodId, authId)
        }

        put("tags") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val tags = call.receive<List<Int>>()
            pokemonController.updateTags(id, tags, authId)
        }

        put("nickName") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val nickName = call.receive<String>()
            pokemonController.updateNickname(id, nickName, authId)
        }

        put("teras-type") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val terasType = call.receive<Int>()
            pokemonController.updateTerasType(id, terasType, authId)
        }
    }
}

private fun PipelineContext<Unit, ApplicationCall>.getAuthId(): String {
    val principal = call.authentication.principal<JWTPrincipal>()
    return principal?.payload?.getClaim("sub")?.asString() ?: throw NotFoundException("認証情報がありません")
}

fun buildRouting() {

}
