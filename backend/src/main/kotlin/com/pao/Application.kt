package com.pao

import com.pao.authentication.JwtService
import com.pao.authentication.hash
import com.pao.plugins.*
import com.pao.repositories.DatabaseFactory
import com.pao.repositories.Repo
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.sessions.*

data class UserSession(val count: Int = 0)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module(db: Repo = Repo(),
                       jwtService: JwtService = JwtService(),
                       hashFunction: (String) -> String = { s: String -> hash(s) }) {
    install(CORS) {
        anyHost()
    }
    DatabaseFactory.init()

    install(Sessions){
        cookie<UserSession>("SESSION"){
            cookie.extensions["SameSite"] = "lax"
        }
    }
    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "com.pao"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = db.findUserByEmail(email)
                user
            }
        }
    }
    configureSerialization()
    configureRouting(db, jwtService, hashFunction)
}
