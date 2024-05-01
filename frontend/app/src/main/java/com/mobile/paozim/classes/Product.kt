package com.mobile.paozim.classes

import com.google.gson.annotations.SerializedName

//import com.google.gson.annotations.SerializedName

data class Product (
//    @SerializedName("id")
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
    val avaliacaoID: Array<Int>
):java.io.Serializable
