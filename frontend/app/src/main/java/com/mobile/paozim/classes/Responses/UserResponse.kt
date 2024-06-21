package com.mobile.paozim.classes.Responses

import com.mobile.paozim.classes.UserStuff.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: String,
    val message: String,
    val user: User?
)
