package com.mobile.paozim.classes.UserStuff

import kotlinx.serialization.Serializable

@Serializable
data class DeleteRequest(
    val email: String,
    val senha: String
)
