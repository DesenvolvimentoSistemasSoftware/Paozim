package com.pao.data.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object OrderTable:Table() {
    val id = integer("id").autoIncrement()
    val sellerID = integer("sellerID").references(SellerTable.id)
    val userEmail = varchar("userEmail", 100).references(UserTable.email)
    val status = varchar("status", 512)
    val dateStart = datetime("dateStart").defaultExpression(CurrentDateTime)
    val dateFinish = datetime("dateFinish")
    val totalPrice = double("totalPrice")
    val shippingPrice = double("shippingPrice")

    override val primaryKey = PrimaryKey(id)
}
