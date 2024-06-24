package com.pao.data.classes.sellerStuff

import kotlinx.serialization.Serializable

@Serializable
data class Seller(
    val nome: String,
    val description: String,
    val image: String,
    val telefone: String,
    val CEP: String,
    val cidade: String,
    val estado: String,
    var bairro: String,
    val numResidencia: Int
)
