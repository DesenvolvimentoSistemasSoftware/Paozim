package com.mobile.paozim.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class UserF(
    val nome:String? = null,
    val email: String,
    val senha: String
)
