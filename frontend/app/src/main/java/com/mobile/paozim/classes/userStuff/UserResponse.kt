package com.mobile.paozim.classes.userStuff

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: String,
    val message: String,
    val user: User?
)
