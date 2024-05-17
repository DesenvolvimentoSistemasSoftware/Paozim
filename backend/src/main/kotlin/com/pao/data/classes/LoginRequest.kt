package com.pao.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val nome: String,
    val email: String,
    val senha: String
)
