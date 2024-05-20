package com.pao.plugins

// imports dos gets, posts, etc
import com.pao.authentication.JwtService
import com.pao.authentication.hash
import com.pao.repositories.Repo
import com.pao.routes.*

// imports essenciais do routing
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

const val BASE_URL = "https://e661-2804-14c-71-4054-8967-f33d-f75e-ce39.ngrok-free.app"

fun Application.configureRouting() {
    routing {
        randomProduct()
        getProduct()

        val db = Repo()
        val jwtService = JwtService()
        val hashFunction = {s: String -> hash(s)}

        UserRoutes(db, jwtService, hashFunction)

        // garante pegar as imagens de uma pasta
        static{
            resources("static")
        }
    }
}
