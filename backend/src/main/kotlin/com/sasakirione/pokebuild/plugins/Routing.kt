package com.sasakirione.pokebuild.plugins

import com.sasakirione.pokebuild.controller.DataController
import com.sasakirione.pokebuild.controller.UserBuildController
import com.sasakirione.pokebuild.controller.UserPokemonController
import com.sasakirione.pokebuild.domain.UserBuild
import com.sasakirione.pokebuild.domain.UserPokemon
import com.sasakirione.pokebuild.domain.UserPokemonValue
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

/**
 * ルーティングの設定
 */
fun Application.configureRouting() {
    routing {
        route("/v1") {
            authenticate {
                route("pokemons") {
                    pokemonRouting()
                }
                route("builds") {
                    buildRouting()
                }
            }
            route("data") {
                dataRouting()
            }
        }
    }
}

/**
 * ユーザーのポケモンに関するルーティング
 */
fun Route.pokemonRouting() {
    val pokemonController: UserPokemonController by inject()

    get {
        val authId = getAuthId()
        call.respond(pokemonController.getUserPokemonList(authId))
    }

    post {
        val authId = getAuthId()
        val pokemon = call.receive<UserPokemon>()
        call.respond(pokemonController.createUserPokemon(pokemon, authId))
    }

    route("{id}") {
        get {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(pokemonController.getUserPokemon(id, authId))
        }

        put {
            val authId = getAuthId()
            val pokemon = call.receive<UserPokemon>()
            call.respond(pokemonController.updatePokemon(pokemon, authId))
        }

        delete {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            call.respond(pokemonController.deletePokemon(id, authId))
        }

        put("ability") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val abilityId = call.receive<Int>()
            call.respond(pokemonController.updateAbility(id, abilityId, authId))
        }

        put("nature") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val natureId = call.receive<Int>()
            call.respond(pokemonController.updateNature(id, natureId, authId))
        }

        put("good") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val goodId = call.receive<Int>()
            call.respond(pokemonController.updateGood(id, goodId, authId))
        }

        put("tags") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val tags = call.receive<List<Int>>()
            call.respond(pokemonController.updateTags(id, tags, authId))
        }

        put("nickName") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val nickName = call.receive<String>()
            call.respond(pokemonController.updateNickname(id, nickName, authId))
        }

        put("teras-type") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val terasType = call.receive<Int>()
            call.respond(pokemonController.updateTerasType(id, terasType, authId))
        }

        put("move") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val move = call.receive<List<Int>>()
            call.respond(pokemonController.updateMoves(id, move, authId))
        }

        put("value") {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val value = call.receive<PokemonValueRequest>()
            call.respond(pokemonController.updateValue(id, value.ev, value.iv, authId))
        }
    }
}

/**
 * ユーザーの構築に関するルーティング
 */
fun Route.buildRouting() {
    val buildController: UserBuildController by inject()

    get {
        val authId = getAuthId()
        call.respond(buildController.getUserBuildList(authId))
    }

    post {
        val build = call.receive<UserBuild>()
        call.respond(buildController.createUserBuild(build))
    }

    route("{id}") {
        get {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            call.respond(buildController.getUserBuild(id, authId))
        }

        put {
            val authId = getAuthId()
            val build = call.receive<UserBuild>()
            call.respond(buildController.updateUserBuild(build, authId))
        }

        delete {
            val authId = getAuthId()
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            call.respond(buildController.deleteUserBuild(id, authId))
        }

        route("pokemons") {
            get {
                val authId = getAuthId()
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(buildController.getUserBuildPokemonList(id, authId))
            }
            route("/{pokemonId}") {
                post {
                    val authId = getAuthId()
                    val buildId = call.parameters["id"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)
                    val pokemonId = call.parameters["pokemonId"]?.toIntOrNull() ?: return@post call.respond(HttpStatusCode.BadRequest)
                    call.respond(buildController.addUserBuildPokemon(buildId, pokemonId, authId))
                }
                delete {
                    val authId = getAuthId()
                    val buildId = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                    val pokemonId = call.parameters["pokemonId"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                    call.respond(buildController.deleteUserBuildPokemon(buildId, pokemonId, authId))
                }
            }
        }
    }
}

/**
 * ポケモンのデータに関するルーティング
 */
fun Route.dataRouting() {
    val dataController: DataController by inject()
    route("{generation}") {
        route("pokemon") {
            get {
                call.respond(dataController.getPokemonList())
            }
            route("{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    call.respond(dataController.getPokemon(id, generation))
                }

                get("ability") {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    call.respond(dataController.getPokemonAbility(id, generation))
                }

                get("moves") {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    call.respond(dataController.getPokemonMoves(id, generation))
                }
            }
        }
        route("goods") {
            get {
                val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(dataController.getGoodsList(generation))
            }
            route("{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    call.respond(dataController.getGoods(id, generation))
                }
            }
        }
        route("abilities") {
            get {
                val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(dataController.getAbilityList(generation))
            }
            route("{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    call.respond(dataController.getAbility(id, generation))
                }
            }
        }
        route("moves") {
            get {
                val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                call.respond(dataController.getMoveList(generation))
            }
            route("{id}") {
                get {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val generation = call.parameters["generation"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                    call.respond(dataController.getMove(id, generation))
                }
            }
        }
        route("tags") {
            get {
                call.respond(dataController.getTagList())
            }
        }
        route("natures") {
            get {
                call.respond(dataController.getNatureList())
            }
        }
    }
}

/**
 * ユーザーのポケモンの個体値を更新する際のリクエストボディ
 */
data class PokemonValueRequest(
    val ev: UserPokemonValue,
    val iv: UserPokemonValue,
)

/**
 * 認証情報からユーザーAuthIDを取得する
 *
 * @return ユーザーAuthID
 */
private fun PipelineContext<Unit, ApplicationCall>.getAuthId(): String {
    val principal = call.authentication.principal<JWTPrincipal>()
    return principal?.payload?.getClaim("sub")?.asString() ?: throw NotFoundException("認証情報がありません")
}

