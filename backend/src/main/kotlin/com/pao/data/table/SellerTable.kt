package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object SellerTable: Table() {
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 100)
    val description = varchar("description", 1024)
    val image = varchar("image", 512)
    val telefone = varchar("telefone", 15)
    val CNPJ = varchar("CNPJ", 20)
    val CEP = varchar("CEP", 20)
    val cidade = varchar("cidade", 50)
    val estado = varchar("estado", 50)
    val endereco = varchar("endereco", 200)
    var bairro = varchar("bairro", 50)
    val numResidencia = integer("numResidencia")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
