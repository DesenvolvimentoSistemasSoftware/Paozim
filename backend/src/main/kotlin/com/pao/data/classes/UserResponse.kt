package com.pao.data.classes

import com.pao.data.classes.userStuff.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: String,
    val message: String,
    val user: User?
)
