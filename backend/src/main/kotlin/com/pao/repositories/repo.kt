package com.pao.repositories

import com.pao.data.classes.User
import com.pao.data.table.UserTable
import org.jetbrains.exposed.sql.insert
import com.pao.repositories.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select


class repo {

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
}
