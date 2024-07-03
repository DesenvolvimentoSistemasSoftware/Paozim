package com.mobile.paozim.classes.userStuff

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var nome: String? = null,
    var senha: String,
    var email: String,
    var CPF: String,
    var telefone: String,
    var CEP: String,
    var cidade: String,
    var estado: String,
    var endereco: String,
    var bairro: String,
    var numResidencia: Int,
    var complemento: String,
    var referencia: String,
)
