package com.pao.data.classes.userStuff

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: String,
    val message: String,
    val user: User?
)
