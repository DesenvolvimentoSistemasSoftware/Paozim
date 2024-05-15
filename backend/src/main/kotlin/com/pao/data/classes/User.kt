package com.pao.data.classes

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

// Allow us to parse the content of this class to json and send it over the network
@Serializable
data class User(
    val nome: String,
    val senha: String,
    val email: String,
):Principal
