package com.pao

import com.pao.authentication.JwtService
import com.pao.authentication.hash
import com.pao.plugins.*
import com.pao.repositories.DatabaseFactory
import com.pao.repositories.Repo
import com.pao.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

data class UserSession(val count: Int = 0)

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CORS) {
        anyHost()
    }
    DatabaseFactory.init()
    val db = Repo()
    val jwtService = JwtService()
    val hashFunction = {s: String -> hash(s)}

    install(Sessions){
        cookie<UserSession>("SESSION"){
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {
        jwt("jwt") {
            verifier(jwtService.verifier)
            realm = "com.pao"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = db.findUserByEmail(email)
                user
            }
        }
    }

    routing {
        randomProduct()
        getProduct()

        UserRoutes(db, jwtService, hashFunction)
        PedidoRoute(db)

        // garante pegar as imagens de uma pasta
        static{
            resources("static")
        }
    }
    configureSerialization()
//    configureRouting()
}
//    irrelevante no momento
//    configureSecurity()
//    configureMonitoring()
