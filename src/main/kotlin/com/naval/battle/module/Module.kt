package com.naval.battle.module

import com.naval.battle.config.JwtConfig
import com.naval.battle.service.UserSource
import com.naval.battle.service.impl.UserSourceImpl
import com.naval.battle.util.user
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.*

fun Application.module() {
    install(CallLogging)
    install(ContentNegotiation) { gson { } }
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        anyHost()
    }

    val userSource: UserSource = UserSourceImpl()
    install(Authentication) {
        /**
         * Setup the JWT authentication to be used in [Routing].
         * If the token is valid, the corresponding [User] is fetched from the database.
         */
        jwt("jwt") {
            verifier(JwtConfig.verifier)
            realm = "ktor.io"
            validate {
                it.payload.getClaim("id").asInt()?.let(userSource::findUserById)
            }
        }
    }

    install(Routing) {

        /**
         * A public login [Route] used to obtain JWTs
         */
        post("login") {
            val credentials = call.receive<UserPasswordCredential>()
            val user = userSource.findUserByCredentials(credentials)
            val token = JwtConfig.makeToken(user)
            call.respond(mapOf("token" to token))
        }

        /**
         * All [Route]s in the authentication block are secured.
         */
        authenticate("jwt") {
            route("user") {

                get {
                    val user = call.user
                    call.respond(mapOf("user" to user))
                }

                put {
                    TODO("All your secret routes can follow here")
                }

            }
        }
    }
}