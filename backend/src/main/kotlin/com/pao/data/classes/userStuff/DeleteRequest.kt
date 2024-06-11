package com.pao.data.classes.userStuff

import kotlinx.serialization.Serializable

// Propriedades não podem ser nulas
@Serializable
data class DeleteRequest(
    val email: String,
    val senha: String
)
