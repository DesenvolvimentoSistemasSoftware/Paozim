package com.pao

import com.auth0.jwt.JWTVerifier
import com.pao.authentication.JwtService
import com.pao.repositories.Repo
import io.ktor.client.request.*

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

import io.ktor.client.*
import io.ktor.client.engine.cio.*

import io.mockk.mockk
import io.mockk.every

import com.pao.routes.randomProduct
import com.pao.routes.getProduct
import io.ktor.server.routing.*

import com.pao.authentication.isEmail

class ApplicationTest {
    // Mock the dependencies
    private val mockRepo = mockk<Repo>()
    private val mockJwtService = mockk<JwtService>()
    private val mockHashFunction: (String) -> String = { it } // Simple identity function for hashing

    private val mockJwtVerifier = mockk<JWTVerifier>()


    @Test
    fun testRandomProduct() {
        every { mockJwtService.getVerifier() } returns mockJwtVerifier

        // Run the test application with the mocked dependencies
        withTestApplication({
            module(mockRepo, mockJwtService, mockHashFunction)
            routing {
                randomProduct()
            }
        }) {
            // Perform a test request
            handleRequest(HttpMethod.Get, "/product/random").apply {
                // Assert the response
                assertEquals(HttpStatusCode.OK, response.status())
//                assertEquals("Expected response", response.content)
                println(response.content)
            }
        }

//        // Make the request
//        val response = client.get("http://127.0.0.1:8000/product/random")
//        assertEquals(HttpStatusCode.OK, response.status)
        // Printa o corpo da resposta
//        println(response.body<String>())
//        // Converte de JSON para um objeto
//        val product = response.body<Product>()
//        assertNotNull(product)
//        // Verifica se o objeto é um produto listado em products
//        assertTrue(products.contains(product))
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
