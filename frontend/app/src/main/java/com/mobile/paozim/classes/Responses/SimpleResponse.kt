package com.mobile.paozim.classes.Responses

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success: String,
    val message: String
)
