package com.pao.data.classes.orderStuff

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Int?,
    val sellerID: Int,
    val userEmail: String,
    val status: String?,
    val timeStart: String?,
    val timeFinish: String?,
    val totalPrice: Double,
    val shippingPrice: Double,
    val shippingDuration: Int,
    val items: List<OrderItem>
)
