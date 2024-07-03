package com.mobile.paozim.classes.orderStuff

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemCallback(
    val itemID: Int,
    val quantity: Int,
    val price: Double,
    var image: String,
    val name: String,
    val myRate: Int
)
