package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object PedidoTable:Table() {
    val id = varchar("id", 512)
    val userEmail = varchar("userEmail", 512).references(UserTable.email)
    val valorTotal = double("valorTotal")
    val status = varchar("status", 512)

    override val primaryKey = PrimaryKey(id, name = "id")
}
