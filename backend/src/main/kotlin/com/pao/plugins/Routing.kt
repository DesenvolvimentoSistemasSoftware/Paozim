package com.pao.plugins

import com.pao.routes.someMessage
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
//        install(ContentNegotiation) {
//            json()
//        }
        someMessage()
//        rounting {
//            get("json/kotlinx-serialization") {
//                call.respond(mapOf("hello" to "world"))
//            }
//        }

//        get("/") {
//            call.respondText("Hello World!")
//        }

        // Static plugin. Try to access `/static/index.html`
        static {
            resources("static")
        }
    }
}
