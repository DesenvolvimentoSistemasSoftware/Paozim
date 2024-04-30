package com.pao.routes

// https://ktor.io/docs/server-requests.html#request_information
import com.pao.data.model.UserInfo

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val BASE_URL = "https://7ebf-190-89-1-239.ngrok-free.app"

// "database" provisória
private val users = listOf(
    UserInfo(id = 1215, nome = "João", email = "pensanumemail@gmail.com", telefone = "5511982667931", imageURL = "$BASE_URL/Images/frances_ou_sal.jpeg"),
    UserInfo(id = 6545, nome = "Pedro", email = "pedroradical@gmail.com", telefone = "5511956485412", imageURL = "$BASE_URL/Images/baguete.jpeg"),
    UserInfo(id = 4511, nome = "André", email = "randomemail@gmail.com", telefone = "5511974545145", imageURL = "$BASE_URL/Images/dafabrica.jpeg"),
    UserInfo(id = 4642, nome = "Glodoaldo", email = "nomefeio@gmail.com", telefone = "5511954841245", imageURL = "$BASE_URL/Images/mofou.jpeg"),
    UserInfo(id = 7875, nome = "Alex", email = "ultimoemail@gmail.com", telefone = "5511997498745", imageURL = "$BASE_URL/Images/queijado.jpeg")
)
fun Route.someMessage() {
    get("/user/pedro") {
        call.respond(HttpStatusCode.OK, listOf(users[1]))
    }
}
fun Route.otherMessage(){

    get("/user") {
        var found = false
        for (user in users) {
            if (call.parameters["nome"] == user.nome) {
                call.respond(HttpStatusCode.OK, user)
                found = true
            }
//            http://localhost:8080/user?nome=João para mandar o parametro
//            http://localhost:8080/user?nome=João&email=blabla@gmail.com
//            cuidado com caracteres ao enviar requests
        }
        if (found == false) {
            call.respond(HttpStatusCode.OK, users[4])
//            call.respond(HttpStatusCode.NotFound)
        }
//        call.respond(HttpStatusCode.OK, users.random())
    }
}
