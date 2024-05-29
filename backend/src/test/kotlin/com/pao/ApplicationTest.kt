package com.pao

import com.pao.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

import com.pao.repositories.Repo
import com.pao.authentication.JwtService
import com.pao.authentication.hash

class ApplicationTest {
    @Test
    fun testModule() = testApplication {
        application {
            module(
                db = Repo(),
                jwtService = JwtService(),
                hashFunction = { s: String -> hash(s) }
            )
        }

        // Testar /product/random (GET)

    }
}
