package com.mobile.paozim.classes.UserStuff

import kotlinx.serialization.Serializable

@Serializable
data class ChangeNameRequest(
    val email: String,
    val senha: String,
    val newName: String
)
