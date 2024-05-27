package com.mobile.paozim.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success: String,
    val message: String
)
