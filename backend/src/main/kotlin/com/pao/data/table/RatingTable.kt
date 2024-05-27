package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object RatingTable: Table() {
    val itemID = integer("itemID").references(ItemTable.id)
    val userEmail = varchar("email", 100).references(UserTable.email)
    val rating = integer("rating")
}
