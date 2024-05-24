package com.pao.data.classes.itemStuff

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: Int,
    val name: String,
    val sellerID: Int,
    val price: Double,
    val stock: Int,
    val image: String,
    val description: String
)
