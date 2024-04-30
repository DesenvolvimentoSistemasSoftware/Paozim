package com.pao.plugins

// imports dos gets, posts, etc
import com.pao.routes.someMessage
import com.pao.routes.otherMessage

// imports essenciais do routing
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

import io.ktor.server.request.*
import io.ktor.server.response.*


fun Application.configureRouting() {
    routing {
        someMessage()
        otherMessage()

        // garante pegar as imagens de uma pasta
        static{
            resources("static")
        }
    }
}
