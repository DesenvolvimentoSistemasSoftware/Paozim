package com.pao.data.classes.orderStuff

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val id: Int,
    val sellerID: Int,
    var status: String,
    val timeStart: String,
    val timeFinish: String,
    val totalPrice: Double,
    val shippingPrice: Double,
)
