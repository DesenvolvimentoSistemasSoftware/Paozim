package com.pao.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object OrderTable:IntIdTable() {
    val sellerID = integer("sellerID")
    val userEmail = varchar("userEmail", 100).references(UserTable.email)
    val status = varchar("status", 512)
    val timeStart = varchar("timeStart", 40)
    val timeFinish = varchar("timeFinish", 40)
    val totalPrice = double("totalPrice")
    val shippingPrice = double("shippingPrice")
}
