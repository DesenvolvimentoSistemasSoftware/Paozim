package com.mobile.paozim.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class Pedido(
    val id:String,
    val valorTotal:Double,
    val status:String
)
