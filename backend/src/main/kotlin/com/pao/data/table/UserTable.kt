package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object UserTable:Table() {
    val nome = varchar("nome", 100)
    val email = varchar("email", 255).uniqueIndex()
    val senha = varchar("senha", 255)
    val telefone = varchar("telefone", 15)
    val CPF = varchar("CPF", 20)
    val CEP = varchar("CEP", 20)
    val cidade = varchar("cidade", 50)
    val estado = varchar("estado", 50)
    val endereco = varchar("endereco", 200)
    var bairro = varchar("bairro", 50)
    val numResidencia = integer("numResidencia")
    val complemento = varchar("complemento", 50)
    val referencia = varchar("referencia", 100)

    override val primaryKey: PrimaryKey = PrimaryKey(email)
}
