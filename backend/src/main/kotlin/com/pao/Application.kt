package com.pao

import com.pao.authentication.JwtService
import com.pao.authentication.hash
import com.pao.plugins.*
import com.pao.repositories.DatabaseFactory
import com.pao.repositories.repo
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(CORS) {
        anyHost()
    }
    DatabaseFactory.init()
    configureRouting()
    configureSerialization()
}

//    irrelevante no momento
//    configureMonitoring()
//    configureSecurity()
