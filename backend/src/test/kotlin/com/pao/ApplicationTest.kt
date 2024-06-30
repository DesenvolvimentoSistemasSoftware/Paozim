package com.pao

import com.auth0.jwt.JWTVerifier
import com.pao.authentication.JwtService
import com.pao.repositories.Repo

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

import io.mockk.mockk
import io.mockk.every

import io.ktor.server.routing.*

import com.pao.authentication.isEmail
import com.pao.data.classes.Product
import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.orderStuff.Order
import com.pao.data.classes.userStuff.User
import com.pao.data.classes.userStuff.UserResponse
import com.pao.routes.*
import io.mockk.coEvery
import kotlinx.serialization.encodeToString

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
            handleRequest(HttpMethod.Get, RANDOM_REQUEST).apply {
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

            handleRequest(HttpMethod.Get, ITENS + "2").apply {
                assertEquals(HttpStatusCode.OK, response.status())

                // Item pode não existir no banco de dados
                assertEquals(
                    ContentType.Application.Json,
                    response.contentType()
                )
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

    @Test
    fun testRegisterRoute() {
        every { mockJwtService.getVerifier() } returns mockJwtVerifier

        // Retorna nulo para simular um usuário a ser cadastrado
        coEvery { mockRepo.findUserByEmail("test@teste.com") } returns null

        val existentUser = User(
            "Eduardo",
            "38maldfjl2###21d",
            "ja_usado@google.com",
            "58395745361",
            "1129240129",
            "0382765",
            "São Paulo",
            "SP",
            "Rua dos Bobos",
            "Vila do Chaves",
            123,
            "Casa 2",
            "Próximo ao mercado"
        )
        coEvery { mockRepo.findUserByEmail("ja_usado@google.com") } returns existentUser

        withTestApplication({
            module(mockRepo, mockJwtService, mockHashFunction)
            routing {
                UserRoute(mockRepo, mockJwtService, mockHashFunction)
            }
        }) {

        val userTeste = User(
            "Gustavaz",
            "38maldfjl2###21d",
            "test@teste.com",
            "58395745361",
            "1129240129",
            "0382765",
            "São Paulo",
            "SP",
            "Rua dos Bobos",
            "Vila do Chaves",
            123,
            "Casa 2",
            "Próximo ao mercado"
        )
        handleRequest(HttpMethod.Post, REGISTER_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(userTeste))
            }.apply {
                // Check the response status code
                assertEquals(HttpStatusCode.OK, response.status())

                // Deserialize the response content
                val simpleResponse = Json.decodeFromString<SimpleResponse>(response.content!!)

                // Check the success field in the response
                assertEquals("true", simpleResponse.success)

                // Check the message field in the response
                assertEquals("Cadastro com sucesso", simpleResponse.message)
            }

            // Apaga o usuário e já testa o DELETE_REQUEST
            handleRequest(HttpMethod.Post, DELETE_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(mapOf(
                    "email" to userTeste.email,
                    "senha" to userTeste.senha
                )))
            }.apply {
                // Check the response status code
                assertEquals(HttpStatusCode.OK, response.status())

                // Deserialize the response content
                val simpleResponse = Json.decodeFromString<SimpleResponse>(response.content!!)

                // Check the success field in the response
                assertEquals("true", simpleResponse.success)

                // Check the message field in the response
                assertEquals("Usuário deletado com sucesso", simpleResponse.message)
            }
        }
    }

    @Test
    fun testLoginRoute() {
        every { mockJwtService.getVerifier() } returns mockJwtVerifier

        val existentUser = User(
            "Eduardo",
            "38maldfjl2###21d",
            "ja_usado@google.com",
            "58395745361",
            "1129240129",
            "0382765",
            "São Paulo",
            "SP",
            "Rua dos Bobos",
            "Vila do Chaves",
            123,
            "Casa 2",
            "Próximo ao mercado"
        )
        coEvery { mockRepo.findUserByEmail("ja_usado@google.com") } returns existentUser

        withTestApplication({
            module(mockRepo, mockJwtService, mockHashFunction)
            routing {
                UserRoute(mockRepo, mockJwtService, mockHashFunction)
            }
        }) {
            val userTeste = User(
                "Gustavaz",
                "38maldfjl2###21d",
                "gustavaz@gustavo.com",
                "58395745361",
                "1129240129",
                "0382765",
                "São Paulo",
                "SP",
                "Rua dos Bobos",
                "Vila do Chaves",
                123,
                "Casa 2",
                "Próximo ao mercado"
            )

            handleRequest(HttpMethod.Post, REGISTER_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(userTeste))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Post, LOGIN_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(mapOf(
                    "email" to userTeste.email,
                    "senha" to userTeste.senha
                )))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val userResponse = Json.decodeFromString<UserResponse>(response.content!!)

                assertEquals("true", userResponse.success)
                assertEquals("Login com sucesso!", userResponse.message)
                assertNotNull(userResponse.user)
            }
        }
    }

    @Test
    fun testUpdateRoute() {
        every { mockJwtService.getVerifier() } returns mockJwtVerifier

        val existentUser = User(
            "Eduardo",
            "38maldfjl2###21d",
            "ja_usado@google.com",
            "58395745361",
            "1129240129",
            "0382765",
            "São Paulo",
            "SP",
            "Rua dos Bobos",
            "Vila do Chaves",
            123,
            "Casa 2",
            "Próximo ao mercado"
        )
        coEvery { mockRepo.findUserByEmail("ja_usado@google.com") } returns existentUser

        withTestApplication({
            module(mockRepo, mockJwtService, mockHashFunction)
            routing {
                UserRoute(mockRepo, mockJwtService, mockHashFunction)
            }
        }) {
            val userTeste = User(
                "Gustavaz",
                "38maldfjl2###21d",
                "gustavo@mano.com",
                "58395745361",
                "1129240129",
                "0382765",
                "São Paulo",
                "SP",
                "Rua dos Bobos",
                "Vila do Chaves",
                123,
                "Casa 2",
                "Próximo ao mercado"
            )

            handleRequest(HttpMethod.Post, REGISTER_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Json.encodeToString(userTeste))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }

            handleRequest(HttpMethod.Post, UPDATE_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Json.encodeToString(
                        mapOf(
                            "email" to userTeste.email,
                            "oldSenha" to userTeste.senha,
                            "newSenha" to "nova_senha"
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val simpleResponse = Json.decodeFromString<SimpleResponse>(response.content!!)

                assertEquals("true", simpleResponse.success)
            }

            handleRequest(HttpMethod.Post, DELETE_REQUEST) {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Json.encodeToString(
                        mapOf(
                            "email" to userTeste.email,
                            "senha" to "nova_senha"
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testOrderRoute() {
        // Testar LIST_ORDER_REQUEST

        every { mockJwtService.getVerifier() } returns mockJwtVerifier

        val existentUser = User(
            "Eduardo",
            "38maldfjl2###21d",
            "ja_usado@google.com",
            "58395745361",
            "1129240129",
            "0382765",
            "São Paulo",
            "SP",
            "Rua dos Bobos",
            "Vila do Chaves",
            123,
            "Casa 2",
            "Próximo ao mercado"
        )
        coEvery { mockRepo.findUserByEmail("ja_usado@google.com") } returns existentUser

        withTestApplication({
            module(mockRepo, mockJwtService, mockHashFunction)
            routing {
                OrderRoute(mockRepo)
                UserRoute(mockRepo, mockJwtService, mockHashFunction)
            }
        }) {
            // Usa o usuário existente
            handleRequest(HttpMethod.Get, LIST_ORDER_REQUEST.replace("{email}", existentUser.email)).apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val orders = Json.decodeFromString<List<Order>>(response.content!!)

                // Usuário não tem pedidos
                assertEquals(0, orders.size)
            }
        }
    }
}
