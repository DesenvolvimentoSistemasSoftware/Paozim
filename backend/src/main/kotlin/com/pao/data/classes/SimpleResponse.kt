package com.pao.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success: String,
    val message: String
)
