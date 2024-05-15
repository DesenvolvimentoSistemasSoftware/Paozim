package com.pao.data.classes

import kotlinx.serialization.Serializable

@Serializable
data class Pedido(
    val id:String,
    val valorTotal:Double,
    val status:String
)
