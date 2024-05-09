package com.pao.classes

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val nome: String,
    val vendedorID: Int,
    val preco: Double,
    var estoque: Int,
    var desconto: Double,
    val imagens: Array<String>,
    val descricao: String,
    val categorias: Array<String>,
    var aveAvaliacao: Double,
    val avaliacaoID: IntArray
)
