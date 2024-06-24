package com.pao.routes

import com.pao.data.classes.SimpleResponse
import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val SELLER = "$API_VERSION/seller"
const val SELLER_REQUEST = "$SELLER/{id}"

fun Route.SellerRoute(db: Repo){
    get(SELLER_REQUEST){
        val id = call.parameters["id"]?.toInt()
        try {
            val seller = db.findSeller(id!!)
            if(seller == null){
                call.respond(HttpStatusCode.NotFound, SimpleResponse("false","Vendedor não encontrado"))
            } else {
                call.respond(HttpStatusCode.OK,seller)
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound,SimpleResponse("false","Vendedor não encontrado"))
        }
    }
}
