package com.mobile.paozim.classes.orderStuff

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
