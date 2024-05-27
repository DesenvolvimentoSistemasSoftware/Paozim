package com.mobile.paozim.data.remote.models

import com.mobile.paozim.classes.UserStuff.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val success: String,
    val message: String,
    val user: User?
)
