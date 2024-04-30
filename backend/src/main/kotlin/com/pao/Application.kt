package com.pao

import com.pao.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CORS) {
        anyHost()
    }
    configureRouting()
    configureSerialization()

//    irrelevante no momento
//    configureMonitoring()
//    irrelevante no momento
//    configureSecurity()
}
