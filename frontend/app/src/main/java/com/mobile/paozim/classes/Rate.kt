package com.mobile.paozim.classes

import kotlinx.serialization.Serializable

@Serializable
data class Rate (
    val itemID: Int,
    val userEmail: String,
    val rating: Int
)
