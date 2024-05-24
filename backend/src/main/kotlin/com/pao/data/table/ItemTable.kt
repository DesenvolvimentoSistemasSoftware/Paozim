package com.pao.data.table

import org.jetbrains.exposed.sql.Table

object ItemTable: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val sellerID = integer("sellerID").references(SellerTable.id)
    val price = double("price")
    val stock = integer("stock")
    val image = varchar("image", 512)
    val description = varchar("description", 1024)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}
