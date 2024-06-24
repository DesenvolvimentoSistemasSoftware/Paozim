package com.pao.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.rateStuff.Rate
import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.server.request.*

const val RATE = "$API_VERSION/rate"
const val RATE_CREATE = "$RATE/create"
const val RATE_ITEM = "$RATE/item/{userEmail}/{itemID}"

fun Route.RateRoute(db: Repo){
    post(RATE_CREATE){
        val rate = try {
            call.receive<Rate>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","Faltam alguns campos"))
            return@post
        }

        try {
            db.addRating(rate)
            call.respond(HttpStatusCode.OK,SimpleResponse("true","Avaliação adicionada com sucesso"))
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Algo deu errado"))
            return@post
        }
    }
    get(RATE_ITEM){
        val userEmail = call.parameters["userEmail"]
        val itemID = call.parameters["itemID"]?.toInt()
        try {
            val rating = db.findRating(itemID!!, userEmail!!)
            if(rating == null){
                call.respond(HttpStatusCode.NotFound,SimpleResponse("false","Avaliação não encontrada"))
            } else {
                call.respond(HttpStatusCode.OK,rating)
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound,SimpleResponse("false","Avaliação não encontrada"))
        }
    }
}
