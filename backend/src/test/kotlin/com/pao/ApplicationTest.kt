package com.pao

import com.pao.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.mockk.*

import com.pao.repositories.Repo
import com.pao.authentication.JwtService
import com.pao.authentication.hash

class ApplicationTest {
    @Test
    fun testGetRandomProduct() = testApplication {
        // Create mocks
        val mockDb = mockk<Repo>()
        val mockJwtService = mockk<JwtService>()
        val mockHashFunction = mockk<(String) -> String>()

        // Define behavior for mocks
        every { mockHashFunction(any()) } returns "hashedValue"
        // You can define other mock behaviors as needed

        // Use the mocks in your application module
        application {
            module(
                db = mockDb,
                jwtService = mockJwtService,
                hashFunction = mockHashFunction
            )
        }

        // Testar /product/random (GET)
        handleRequest(HttpMethod.Get, "/product/random").apply {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}
