package com.pao.data.classes.userStuff

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRequest(
    val email: String,
    val oldSenha: String,
    var newSenha: String,
    val CEP: String,
    val cidade: String,
    val estado: String,
    val endereco: String,
    var bairro: String,
    val numResidencia: Int,
    val complemento: String,
    val referencia: String
)
