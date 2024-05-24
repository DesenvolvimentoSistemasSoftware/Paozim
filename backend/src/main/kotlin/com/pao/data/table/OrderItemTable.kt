package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object OrderItemTable: Table() {
    val orderID = integer("orderID").references(OrderTable.id)
    val itemID = integer("itemID").references(ItemTable.id)
    val quantity = integer("quantity")
    val price = double("price")
    val total = double("total")
}
