package com.mobile.paozim.classes.OrderStuff

import kotlinx.serialization.Serializable

@Serializable
data class OrderCallback (
    val id: Int,
    val sellerID: Int,
    val status: String,
    val timeStart: String,
    val timeFinish: String,
    val totalPrice: Double,
    val shippingPrice: Double
)
