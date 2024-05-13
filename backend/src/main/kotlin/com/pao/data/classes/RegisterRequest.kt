package com.pao.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val nome: String,
    val senha: String,
    val email: String
)
