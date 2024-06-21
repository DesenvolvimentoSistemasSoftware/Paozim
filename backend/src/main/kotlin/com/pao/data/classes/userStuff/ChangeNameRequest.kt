package com.pao.data.classes.userStuff

import kotlinx.serialization.Serializable

@Serializable
data class ChangeNameRequest(
    val email: String,
    val senha: String,
    val newName: String
)
