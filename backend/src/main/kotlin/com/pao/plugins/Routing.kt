package com.pao.plugins

// imports dos gets, posts, etc
import com.pao.authentication.JwtService
import com.pao.authentication.hash
import com.pao.repositories.repo
import com.pao.routes.*

// imports essenciais do routing
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

const val BASE_URL = "https://7ebf-190-89-1-239.ngrok-free.app"

fun Application.configureRouting() {
    routing {
        randomProduct()
        getProduct()

        val db = repo()
        val jwtService = JwtService()
        val hashFunction = {s: String -> hash(s)}

        UserRoutes(db, jwtService, hashFunction)
        otherMessage()

        // garante pegar as imagens de uma pasta
        static{
            resources("static")
        }
    }
}
