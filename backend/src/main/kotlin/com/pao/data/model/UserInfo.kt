package com.pao.data.model

import kotlinx.serialization.Serializable

// Allow us to parse the content of this class to json and send it over the network
@Serializable
data class UserInfo(
    val id: Int,
    val nome: String,
    val email: String,
    val telefone: String,
    val imageURL: String
)
