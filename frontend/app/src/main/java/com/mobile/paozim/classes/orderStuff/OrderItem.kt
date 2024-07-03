package com.mobile.paozim.classes.orderStuff

import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(
    val itemID: Int,
    val quantity: Int,
    val price: Double,
)
