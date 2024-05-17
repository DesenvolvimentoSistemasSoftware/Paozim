package com.pao.repositories

import com.pao.data.classes.Pedido
import com.pao.data.classes.User
import com.pao.data.table.PedidoTable
import com.pao.data.table.UserTable
import org.jetbrains.exposed.sql.insert
import com.pao.repositories.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.and

class Repo {

    // User functions in database
    suspend fun addUser(user: User) {
        dbQuery{
            UserTable.insert { ut->
                ut[UserTable.email] = user.email
                ut[UserTable.nome] = user.nome
                ut[UserTable.senha] = user.senha
            }
        }
    }
    suspend fun findUserByEmail(email:String) = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map { rowToUser(it) }
            .singleOrNull()
    }
    private fun rowToUser(row:ResultRow?): User? {
        if (row == null) {
            return null
        }
        return User(
            nome = row[UserTable.nome],
            email = row[UserTable.email],
            senha = row[UserTable.senha]
        )
    }

    // Pedido functions in database
    suspend fun addPedido(ord: Pedido, email: String) {
        dbQuery {
            PedidoTable.insert { pt->
                pt[PedidoTable.id] = ord.id
                pt[PedidoTable.userEmail] = email
                pt[PedidoTable.valorTotal] = ord.valorTotal
                pt[PedidoTable.status] = ord.status
            }
        }
    }
    suspend fun getAllPedidos(email: String):List<Pedido> = dbQuery {
        PedidoTable.select {
            PedidoTable.userEmail.eq(email)
        }.mapNotNull { rowToPedido(it) }
    }
    suspend fun updatePedidoStatus(id:String, email:String, newStatus:String){
        dbQuery {
            PedidoTable.update(
                where = {
                    (PedidoTable.id eq id) and (PedidoTable.userEmail eq email)
                },
            ){
                it[PedidoTable.status] = newStatus
            }
        }
    }
    private fun rowToPedido(row:ResultRow?): Pedido? {
        if (row == null) {
            return null
        }
        return Pedido(
            id = row[PedidoTable.id],
            valorTotal = row[PedidoTable.valorTotal],
            status = row[PedidoTable.status]
        )
    }
}
