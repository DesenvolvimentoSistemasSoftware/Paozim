package com.pao.data.classes.orderStuff

import kotlinx.serialization.Serializable

@Serializable
class SignatureOrder(
    val productId: Int,
    val userEmail: String
)
