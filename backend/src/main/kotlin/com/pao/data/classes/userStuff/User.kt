package com.pao.data.classes.userStuff

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val nome: String,
    var senha: String,
    val email: String,
    val CPF: String,
    val telefone: String,
    val CEP: String,
    val cidade: String,
    val estado: String,
    val endereco: String,
    var bairro: String,
    val numResidencia: Int,
    val complemento: String,
    val referencia: String
    // Adicionar foto usuário
    // Adicionar data de cadastro
):Principal
