package com.mobile.paozim.classes.UserStuff

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val senha: String
)
