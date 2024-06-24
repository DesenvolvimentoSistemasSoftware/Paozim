package com.pao.data.classes.orderStuff

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemResponse(
    val itemID: Int,
    val quantity: Int,
    val price: Double,
    val image: String,
    val name: String,
    var myRate: Int
)
