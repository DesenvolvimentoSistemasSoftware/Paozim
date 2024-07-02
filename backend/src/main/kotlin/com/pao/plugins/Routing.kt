package com.pao.plugins

import com.pao.authentication.JwtService
import com.pao.repositories.Repo
import com.pao.routes.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

const val API_VERSION = "/v1"

fun Application.configureRouting(db:Repo, jwtService:JwtService, hashFunction: (String) -> String) {
    routing {
        UserRoute(db, jwtService, hashFunction)
        ItemRoute(db)
        OrderRoute(db)
        RateRoute(db)
        SellerRoute(db)
        SignatureRoute(db)

        static{
            resources("static")
        }
    }
}
