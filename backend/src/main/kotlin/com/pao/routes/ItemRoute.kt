package com.pao.routes

import com.pao.data.classes.SimpleResponse
import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val ITENS = "$API_VERSION/itens"
const val RANDOM_REQUEST = "$ITENS/random"
const val ITEM_REQUEST = "$ITENS/{id}"
const val SELLER_ITEM_REQUEST = "$ITENS/seller/{sellerID}"
const val NAME_ITEM_REQUEST = "$ITENS/name/{name}"

fun Route.ItemRoute(db: Repo){
    get(RANDOM_REQUEST){
        try {
            call.respond(HttpStatusCode.OK, db.randomItem()!!)
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
        }
    }
    get(ITEM_REQUEST){
        val id = call.parameters["id"]?.toInt()
        try {
            val item = db.findItemById(id!!)
            if(item == null){
                call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
            } else {
                call.respond(HttpStatusCode.OK, item)
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
        }
    }
    get(SELLER_ITEM_REQUEST){
        val sellerID = call.parameters["sellerID"]?.toInt()
        try {
            val items = db.findItemsBySeller(sellerID!!)
            call.respond(HttpStatusCode.OK, items)
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
        }
    }
    get(NAME_ITEM_REQUEST){
        val name = call.parameters["name"]
        try {
            val items = db.findItemsByName(name!!)
            call.respond(HttpStatusCode.OK, items)
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
        }
    }
}
