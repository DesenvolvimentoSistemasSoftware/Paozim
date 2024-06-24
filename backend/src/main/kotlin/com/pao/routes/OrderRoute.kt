package com.pao.routes

import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.orderStuff.Order
import io.ktor.server.request.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val ORDER = "$API_VERSION/order"
const val ORDER_REQUEST = "$ORDER/{id}"
const val CREATE_ORDER_REQUEST = "$ORDER/create"
const val LIST_ORDER_REQUEST = "$ORDER/list/{email}"

fun Route.OrderRoute(db: Repo){
    post(CREATE_ORDER_REQUEST){
        val orderRequest = try {
            call.receive<Order>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","Faltam alguns campos"))
            return@post
        }

        try {
            db.addOrder(orderRequest)
            call.respond(HttpStatusCode.OK,SimpleResponse("true","Pedido criado com sucesso"))
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Algo deu errado"))
            return@post
        }
    }
    get(LIST_ORDER_REQUEST){
        val email = call.parameters["email"]
        try {
            val orders = db.findOrdersByUser(email!!)
            for(order in orders){
                if(order.status == "Pendente"){
                    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    val currentTimeParsed = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    val orderTime = LocalDateTime.parse(order.timeFinish, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    if(currentTimeParsed >= orderTime) {
                        db.updateStatus(order.id, "Entregue")
                        order.status = "Entregue"
                    }
                }
            }

            call.respond(HttpStatusCode.OK,orders)
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound,SimpleResponse("false","Algo deu errado"))
        }
    }
    get(ORDER_REQUEST){
        val id = call.parameters["id"]?.toInt()
        try {
            launch {
                val items = db.findOrderItems(id!!)
                val userEmail = db.findUserByOrder(id)
                for (item in items) {
                    val rating = db.findRating(item.itemID, userEmail)
                    if (rating != null) {
                        item.myRate = rating
                    }
                }
                call.respond(HttpStatusCode.OK,items)
            }.join()
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound,SimpleResponse("false","Algo deu errado"))
        }
    }
}
