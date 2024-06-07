package com.pao

import com.auth0.jwt.JWTVerifier
import com.pao.authentication.JwtService
import com.pao.repositories.Repo

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

import io.mockk.mockk
import io.mockk.every

import com.pao.routes.randomProduct
import com.pao.routes.products
import com.pao.routes.getProduct
import io.ktor.server.routing.*

import com.pao.authentication.isEmail
import com.pao.data.classes.Product

import kotlinx.serialization.json.*

class ApplicationTest {
    private val mockRepo = mockk<Repo>()
    private val mockJwtService = mockk<JwtService>()
    private val mockHashFunction: (String) -> String = { it }

    private val mockJwtVerifier = mockk<JWTVerifier>()


    @Test
    fun testProducts() {
        every { mockJwtService.getVerifier() } returns mockJwtVerifier

        withTestApplication({
            module(mockRepo, mockJwtService, mockHashFunction)
            routing {
                randomProduct()
                getProduct()
            }
        }) {
            handleRequest(HttpMethod.Get, "/product/random").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                assertEquals(
                    ContentType.Application.Json,
                    response.contentType()
                )

                val product = Json.decodeFromString<Product>(response.content!!)

                // Verificar se tem os campos da classe Product
                assertNotNull(product.id)
                assertNotNull(product.nome)
                assertNotNull(product.preco)
                assertNotNull(product.estoque)
                assertNotNull(product.desconto)
            }

            handleRequest(HttpMethod.Get, "/product/2").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                assertEquals(
                    ContentType.Application.Json,
                    response.contentType()
                )

                val product = Json.decodeFromString<Product>(response.content!!)

                // Verificar se tem os campos da classe Product
                assertNotNull(product.id)
                assertNotNull(product.nome)
                assertNotNull(product.preco)
                assertNotNull(product.estoque)
                assertNotNull(product.desconto)
            }
        }
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
