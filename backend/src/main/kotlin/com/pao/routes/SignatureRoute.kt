package com.pao.routes

import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.orderStuff.SignatureOrder
import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val SIGNATURE = "$API_VERSION/signature"
const val ADD_SIGNATURE = "$SIGNATURE/create"
const val GET_SIGNATURE = "$SIGNATURE/{email}"

fun Route.SignatureRoute(db: Repo){
    post(ADD_SIGNATURE) {
        val signatureOrder = try {
            call.receive<SignatureOrder>()
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, SimpleResponse("false", "Faltam alguns campos"))
            return@post
        }
        try {
            db.addSignature(signatureOrder)
            call.respond(HttpStatusCode.OK, SimpleResponse("true", "Item assinado com sucesso"))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, SimpleResponse("false", e.message ?: "Algo deu errado"))
            return@post
        }
    }
    get(GET_SIGNATURE){
        val email = call.parameters["email"]
        try {
            val items = db.findSignaturedItems(email!!)
            call.respond(HttpStatusCode.OK, items)
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
        }
    }
}
