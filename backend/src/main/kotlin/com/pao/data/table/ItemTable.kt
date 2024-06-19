package com.pao.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object ItemTable: IntIdTable() {
    val name = varchar("name", 100)
    val sellerID = integer("sellerID").references(SellerTable.id)
    val price = double("price")
    val stock = integer("stock")
    val image = varchar("image", 512)
    val description = varchar("description", 1024)
}
