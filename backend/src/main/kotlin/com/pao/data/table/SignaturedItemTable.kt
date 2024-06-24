package com.pao.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object SignaturedItemTable: IntIdTable() {
    val itemId = integer("itemId").references(ItemTable.id)
    val userEmail = varchar("userEmail", 100).references(UserTable.email)
    val frequency = varchar("frequency", 50)
}
