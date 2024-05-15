package com.pao.routes

import com.pao.data.classes.Pedido
import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.User
import com.pao.repositories.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val PEDIDO = "$API_VERSION/pedido"
const val CREATE_PEDIDO = "$PEDIDO/create"
const val UPDADE_PEDIDO = "$PEDIDO/update"

fun Route.PedidoRoute(
    db: Repo
) {
    authenticate("jwt"){
        post(CREATE_PEDIDO){
            val pedido = try {
                call.receive<Pedido>()
            } catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest, SimpleResponse("false","Missing some fields"))
                return@post
            }

            try {
                val email = call.principal<User>()!!.email
                db.addPedido(pedido,email)
                call.respond(HttpStatusCode.OK,SimpleResponse("true","Pedido criado"))
            } catch (e:Exception){
                call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Some error ocurred"))
            }
        }
        get(PEDIDO){
            try {
                val email = call.principal<User>()!!.email
                val pedidos = db.getAllPedidos(email)
                call.respond(HttpStatusCode.OK,pedidos)
            } catch (e:Exception){
                call.respond(HttpStatusCode.Conflict, emptyList<Pedido>())
            }
        }
        post(UPDADE_PEDIDO){
            val pedido = try {
                call.receive<Pedido>()
            } catch (e:Exception){
                call.respond(HttpStatusCode.BadRequest, SimpleResponse("false","Missing some fields"))
                return@post
            }

            try {
                val email = call.principal<User>()!!.email
                db.updatePedidoStatus(pedido.id, email, pedido.status)
                call.respond(HttpStatusCode.OK,SimpleResponse("true","Pedido atualizado"))
            } catch (e:Exception){
                call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Some error ocurred"))
            }
        }
    }
}
