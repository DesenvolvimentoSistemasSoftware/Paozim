package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object UserTable:Table() {
    val nome = varchar("nome", 512)
    val email = varchar("email", 512)
    val senha = varchar("senha", 512)

    override val primaryKey: PrimaryKey = PrimaryKey(email)
}
