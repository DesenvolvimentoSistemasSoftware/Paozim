package com.pao.data.table

import org.jetbrains.exposed.dao.id.IntIdTable

object SignatureTable: IntIdTable() {
    val itemID = integer("itemId").references(ItemTable.id)
    val userEmail = varchar("userEmail", 100).references(UserTable.email)
    val totalPrice = double("totalPrice")
    val quantity = integer("quantity")
    val status = varchar("status", 50)
    val arriveTime = varchar("arriveTime", 40)
    val dayStart = varchar("dayStart", 40)
    val frequency = varchar("frequency", 50)
    val totalPeriod = integer("totalPeriod")
}
