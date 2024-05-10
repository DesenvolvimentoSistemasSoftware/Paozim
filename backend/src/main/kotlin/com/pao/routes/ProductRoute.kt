package com.pao.routes

import com.pao.classes.Product
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val BASE_URL = "https://124c-143-107-45-1.ngrok-free.app"

// "database" provisória
val products = listOf<Product>(
    Product(1,"Baguete", 5001,14.90,30,
        0.0,arrayOf("$BASE_URL/Images/baguete.jpeg"),"Deliciosa baguete direto do forno para sua casa",
        arrayOf("Pão","Salgado","Café da manhã"), 4.5, intArrayOf(8001,8002,8003,8004,8005)
    ),
    Product(2,"Pão de Forma", 5001,8.90,30,
        0.0,arrayOf("$BASE_URL/Images/dafabrica.jpeg"),"Pão de forma da melhor qualidade",
        arrayOf("Pão","Salgado","Café da manhã","industrial"), 4.0, intArrayOf(8071,8102,8303,8804,8505)
    ),
    Product(3,"Pão Francês", 5001,0.5,50,
        0.0,arrayOf("$BASE_URL/Images/frances_ou_sal.jpeg"),"Esse não pode faltar nas manhãs de ninguém",
        arrayOf("Pão","Salgado","Café da manhã"), 4.8, intArrayOf(10,12,13,14,15)
    ),
    Product(4,"Pão de Queijo", 5001,2.90,30,
        0.0,arrayOf("$BASE_URL/Images/queijado.jpeg"),"Pão de queijo mineiro bem recheado de queijo",
        arrayOf("Pão","Salgado","Café da manhã"), 4.5, intArrayOf(21,22,33,24,25)
    ),
    Product(5,"Mofado", 5001,0.0,5,
        0.0,arrayOf("$BASE_URL/Images/mofou.jpeg"),"Por que estou vendendo isso?",
        arrayOf("?"), 0.0, intArrayOf(0)
    )
)
fun Route.randomProduct() {
    get("product/random") {
        call.respond(HttpStatusCode.OK, products.random())
    }
}
fun Route.getProduct() {
    get("product/{id}") {
        val id = call.parameters["id"]?.toInt()
        var found = false
        for(product in products)
            if(product.id == id){
                call.respond(HttpStatusCode.OK, product)
                found = true
            }
        if(!found)
            call.respond(HttpStatusCode.NotFound)
    }
}
