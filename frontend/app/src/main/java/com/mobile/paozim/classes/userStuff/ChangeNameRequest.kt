package com.mobile.paozim.classes.userStuff

import kotlinx.serialization.Serializable

@Serializable
data class ChangeNameRequest(
    val email: String,
    val senha: String,
    val newName: String
)
