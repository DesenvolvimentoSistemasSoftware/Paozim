package com.pao.routes
// https://ktor.io/docs/server-requests.html#request_information
import com.pao.data.model.UserInfo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

//para rodar nesta máquina
//private const val BASE_URL = "http://localhost:8080"
//para rodar na usp
//private const val BASE_URL = "http://172.19.254.15:8080"
//para rodar na casa do gustavo
private const val BASE_URL = "http://192.168.0.135:8080"
private val users = listOf(
    UserInfo(id = 1215, nome = "João", email = "pensanumemail@gmail.com", telefone = "5511982667931", imageURL = "$BASE_URL/Images/frances_ou_sal.jpeg"),
    UserInfo(id = 6545, nome = "Pedro", email = "pedroradical@gmail.com", telefone = "5511956485412", imageURL = "$BASE_URL/Images/baguete.jpeg"),
    UserInfo(id = 4511, nome = "André", email = "randomemail@gmail.com", telefone = "5511974545145", imageURL = "$BASE_URL/Images/dafabrica.jpeg"),
    UserInfo(id = 4642, nome = "Glodoaldo", email = "nomefeio@gmail.com", telefone = "5511954841245", imageURL = "$BASE_URL/Images/mofou.jpeg"),
    UserInfo(id = 7875, nome = "Alex", email = "ultimoemail@gmail.com", telefone = "5511997498745", imageURL = "$BASE_URL/Images/queijado.jpeg")
)
private val invalido = UserInfo(id = 0, nome = "invalido", email = "invalido", telefone = "0000000000000", imageURL = "$BASE_URL/Images/mofou.jpeg")

fun Route.someMessage(){
    get("/user/pedro") {
        call.respond(HttpStatusCode.OK, users[1])
    }

    get("/user") {
        var found = false
        for(user in users){
//            http://localhost:8080/user?nome=João para mandar o parametro
//            http://localhost:8080/user?nome=João&email=blabla@gmail.com
//            cuidado com caracteres ao enviar requests
            if(call.parameters["nome"] == user.nome){
                call.respond(HttpStatusCode.OK, user)
                found = true
            }
        }
        if(found == false){
//            call.respond(HttpStatusCode.OK, invalido)
            call.respond(HttpStatusCode.NotFound)
        }
//        call.respond(HttpStatusCode.OK,users.random())
    }
}

class UserRoute {
}
