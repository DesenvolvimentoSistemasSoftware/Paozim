package com.pao.repositories

import com.pao.data.classes.itemStuff.Item
import com.pao.data.classes.orderStuff.Order
import com.pao.data.classes.orderStuff.OrderItemResponse
import com.pao.data.classes.orderStuff.OrderResponse
import com.pao.data.classes.userStuff.UpdateRequest
import com.pao.data.classes.userStuff.User
import com.pao.data.table.ItemTable
import com.pao.data.table.OrderItemTable
import com.pao.data.table.OrderTable
import com.pao.data.table.UserTable
import com.pao.repositories.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime

class Repo {
    // User functions in database
    suspend fun addUser(user: User) {
        dbQuery {
            UserTable.insert { ut ->
                ut[UserTable.email] = user.email
                ut[UserTable.nome] = user.nome
                ut[UserTable.senha] = user.senha
                ut[UserTable.CPF] = user.CPF
                ut[UserTable.telefone] = user.telefone
                ut[UserTable.CEP] = user.CEP
                ut[UserTable.cidade] = user.cidade
                ut[UserTable.estado] = user.estado
                ut[UserTable.endereco] = user.endereco
                ut[UserTable.bairro] = user.bairro
                ut[UserTable.numResidencia] = user.numResidencia
                ut[UserTable.complemento] = user.complemento
                ut[UserTable.referencia] = user.referencia
            }
        }
    }
    suspend fun findUserByEmail(email: String): User? {
        return dbQuery {
            UserTable
                .select { UserTable.email.eq(email) }
                .mapNotNull { rowToUser(it) }
                .singleOrNull()
        }
    }
    suspend fun updateUser(newUserInfo: UpdateRequest) {
        dbQuery {
            UserTable.update(
                where = { UserTable.email eq newUserInfo.email })
            {
                it[UserTable.senha] = newUserInfo.newSenha
                it[UserTable.CEP] = newUserInfo.CEP
                it[UserTable.cidade] = newUserInfo.cidade
                it[UserTable.estado] = newUserInfo.estado
                it[UserTable.endereco] = newUserInfo.endereco
                it[UserTable.bairro] = newUserInfo.bairro
                it[UserTable.numResidencia] = newUserInfo.numResidencia
                it[UserTable.complemento] = newUserInfo.complemento
                it[UserTable.referencia] = newUserInfo.referencia
            }
        }
    }
    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) {
            return null
        }
        return User(
            nome = row[UserTable.nome],
            email = row[UserTable.email],
            senha = row[UserTable.senha],
            CPF = row[UserTable.CPF],
            telefone = row[UserTable.telefone],
            CEP = row[UserTable.CEP],
            cidade = row[UserTable.cidade],
            estado = row[UserTable.estado],
            endereco = row[UserTable.endereco],
            bairro = row[UserTable.bairro],
            numResidencia = row[UserTable.numResidencia],
            complemento = row[UserTable.complemento],
            referencia = row[UserTable.referencia]
        )
    }
    suspend fun deleteUser(email: String) {
        val user = findUserByEmail(email) ?: throw NoSuchElementException("User with email $email not found")
        dbQuery {
            UserTable.deleteWhere { UserTable.email eq email }
        }
    }

    suspend fun changeUserName(email: String, newName: String) {
        dbQuery {
            UserTable.update(
                where = { UserTable.email eq email })
            {
                it[UserTable.nome] = newName
            }
        }
    }

    // Itens function in database
    suspend fun randomItem(): Item? {
        return dbQuery {
            ItemTable
                .select { ItemTable.stock.greater(0) }
                .mapNotNull { rowToItem(it) }
                .shuffled()
                .firstOrNull()
        }
    }
    suspend fun findItemById(id: Int): Item? {
        return dbQuery {
            ItemTable
                .select { ItemTable.id.eq(id) }
                .mapNotNull { rowToItem(it) }
                .singleOrNull()
        }
    }
    suspend fun findItemsBySeller(sellerID: Int): List<Item> {
        return dbQuery {
            ItemTable
                .select { ItemTable.sellerID.eq(sellerID) }
                .mapNotNull { rowToItem(it) }
        }
    }
    suspend fun findItemsByName(name: String): List<Item> {
        return dbQuery {
            ItemTable
                .select { ItemTable.name.lowerCase().like("%$name%") }
                .mapNotNull { rowToItem(it) }
        }
    }
    private fun rowToItem(row: ResultRow?): Item? {
        if (row == null) {
            return null
        }
        return Item(
            id = row[ItemTable.id].value,
            name = row[ItemTable.name],
            sellerID = row[ItemTable.sellerID],
            price = row[ItemTable.price],
            stock = row[ItemTable.stock],
            image = row[ItemTable.image],
            description = row[ItemTable.description]
        )
    }

    // Order functions in database
    suspend fun addOrder(order: Order) {
        val timeStart = LocalDateTime.now()
        val timeFinish = timeStart.plusMinutes(order.shippingDuration.toLong())

        val id = dbQuery {
            OrderTable.insertAndGetId { ot ->
                ot[OrderTable.sellerID] = order.sellerID
                ot[OrderTable.userEmail] = order.userEmail
                ot[OrderTable.status] = "Pendente"
                ot[OrderTable.timeStart] = timeStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ot[OrderTable.timeFinish] = timeFinish.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ot[OrderTable.totalPrice] = order.totalPrice
                ot[OrderTable.shippingPrice] = order.shippingPrice
            }.value
        }
        for (item in order.items) {
            dbQuery {
                OrderItemTable.insert { oit ->
                    oit[OrderItemTable.orderID] = id
                    oit[OrderItemTable.itemID] = item.itemID
                    oit[OrderItemTable.quantity] = item.quantity
                    oit[OrderItemTable.price] = item.price
                }
            }
        }
    }
    suspend fun findOrdersByUser(email: String): List<OrderResponse> {
        return dbQuery {
            OrderTable
                .select { OrderTable.userEmail.eq(email) }
                .mapNotNull { rowToOrder(it) }
        }
    }
    suspend fun updateStatus(orderID: Int, newStatus: String) {
        dbQuery {
            OrderTable.update(
                where = { OrderTable.id eq orderID })
            {
                it[OrderTable.status] = newStatus
            }
        }
    }
    private fun rowToOrder(row: ResultRow?): OrderResponse? {
        if (row == null) {
            return null
        }
        return OrderResponse(
            id = row[OrderTable.id].value,
            sellerID = row[OrderTable.sellerID],
            status = row[OrderTable.status],
            timeStart = row[OrderTable.timeStart],
            timeFinish = row[OrderTable.timeFinish],
            totalPrice = row[OrderTable.totalPrice],
            shippingPrice = row[OrderTable.shippingPrice],
        )
    }
    suspend fun findOrderItems(orderID: Int): List<OrderItemResponse> {
        return dbQuery {
            (OrderItemTable innerJoin ItemTable)
                .select { OrderItemTable.orderID eq orderID }
                .map {
                    OrderItemResponse(
                        itemID = it[OrderItemTable.itemID],
                        quantity = it[OrderItemTable.quantity],
                        price = it[OrderItemTable.price],
                        image = it[ItemTable.image],
                        name = it[ItemTable.name]
                    )
                }
        }
    }
}
