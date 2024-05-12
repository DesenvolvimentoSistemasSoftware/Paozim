package com.pao.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val senha: String
)
