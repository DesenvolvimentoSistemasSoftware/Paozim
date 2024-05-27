package com.pao.plugins

import com.pao.authentication.JwtService
import com.pao.repositories.Repo
import com.pao.routes.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

const val API_VERSION = "/v1"
const val BASE_URL = "https://39b6-2804-14c-71-4054-3129-38c7-3b1a-2a9.ngrok-free.app"

fun Application.configureRouting(db:Repo, jwtService:JwtService, hashFunction: (String) -> String) {
    routing {
        UserRoute(db, jwtService, hashFunction)
        ItemRoute(db)

        randomProduct()
        getProduct()


        static{
            resources("static")
        }
    }
}
