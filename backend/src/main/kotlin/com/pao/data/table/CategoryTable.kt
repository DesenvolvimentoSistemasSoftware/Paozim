package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object CategoryTable: Table(){
    val itemID = integer("itemID").references(ItemTable.id)
    val category = varchar("category", 50)
}
