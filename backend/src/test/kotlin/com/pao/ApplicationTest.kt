package com.pao

import io.ktor.client.request.*
import io.ktor.client.statement.*

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

import io.ktor.client.*
import io.ktor.client.engine.cio.*

import io.mockk.every
import io.mockk.mockk

import com.pao.repositories.Repo
import com.pao.authentication.JwtService
import com.pao.data.classes.Product

import com.pao.routes.randomProduct
import com.pao.routes.products
import com.pao.routes.getProduct
import io.ktor.client.call.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import com.pao.authentication.isEmail

class ApplicationTest {
    @Test
    fun testRandomProduct() = testApplication {
        // Define the application module for testing
        application {
            // Use the existing routes from your main project
            routing {
                route("/product") {
                    get("/random") {
                        call.respond(HttpStatusCode.OK, products.random())
                    }
                }
            }
        }

        // Create the client
        val client = HttpClient(CIO)

        // Make the request
        val response = client.get("/product/random")
        assertEquals(HttpStatusCode.OK, response.status)
        // Converte de JSON para um objeto
        val product = response.body<Product>()
        assertNotNull(product)
        // Verifica se o objeto é um produto listado em products
        assertTrue(products.contains(product))
    }

    @Test
    fun testIsEmail() {
        assertEquals(true, isEmail("teste@teste.com"))
        assertEquals(false, isEmail("teste.com"))
        assertEquals(false, isEmail("teste@teste"))
        assertEquals(true, isEmail("eduardo@gmail.com"))
        assertEquals(true, isEmail("gustavo@gmail.com"))
    }
}
