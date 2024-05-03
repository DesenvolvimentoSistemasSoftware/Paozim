package com.pao.plugins

// imports dos gets, posts, etc
import com.pao.routes.someMessage
import com.pao.routes.otherMessage
import com.pao.routes.randomProduct

// imports essenciais do routing
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        randomProduct()
        someMessage()
        otherMessage()

        // garante pegar as imagens de uma pasta
        static{
            resources("static")
        }
    }
}
