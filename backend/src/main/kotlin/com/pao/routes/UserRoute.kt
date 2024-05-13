package com.pao.routes

// https://ktor.io/docs/server-requests.html#request_information
import com.pao.authentication.hash
import com.pao.data.classes.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.pao.authentication.JwtService
import com.pao.data.classes.LoginRequest
import com.pao.data.classes.RegisterRequest
import com.pao.data.classes.SimpleResponse
import com.pao.plugins.BASE_URL
import com.pao.repositories.repo
import io.ktor.server.locations.*
import io.ktor.server.request.*

// "database" provisória
private val users = listOf(
    User(nome = "João", senha="12345678",email = "pensanumemail@gmail.com"),
    User(nome = "Pedro", senha = "Pedroburro", email = "pedroradical@gmail.com"),
    User(nome = "André", senha = "andrepinheiro", email = "randomemail@gmail.com"),
    User(nome = "Glodoaldo", senha = "pqmeodeias", email = "nomefeio@gmail.com"),
    User(nome = "Alex", senha = "ALEXCHAD", email = "ultimoemail@gmail.com")
)
const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"

fun Route.otherMessage(){
    get("/token"){
        val email = call.request.queryParameters["email"]!!
        val password = call.request.queryParameters["password"]!!
        val username = call.request.queryParameters["username"]!!

        val user = User(nome = username, senha = hash(password), email = email)
        call.respond(JwtService().generateToken(user))
    }
}
fun Route.UserRoutes(db:repo, jwtService:JwtService, hashFunction: (String) -> String){
    post(REGISTER_REQUEST){
        val registerRequest = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Missing some fields"))
            return@post
        }

        try {
            val newUser = User(
                nome = registerRequest.nome,
                senha = hashFunction(registerRequest.senha),
                email = registerRequest.email
            )
            db.addUser(newUser)
            call.respond(HttpStatusCode.OK,SimpleResponse(true,jwtService.generateToken(newUser)))
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse(false,e.message ?: "Some error ocurred"))
            return@post
        }
    }
    post(LOGIN_REQUEST){
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Missing some fields"))
            return@post
        }
        try {
            val user = db.findUserByEmail(loginRequest.email)
            if(user == null){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"User not found"))
            } else {
                if(user.senha == hashFunction(loginRequest.senha)){
                    call.respond(HttpStatusCode.OK,SimpleResponse(true,jwtService.generateToken(user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest,SimpleResponse(false,"Wrong password"))
                }
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse(false,e.message ?: "Some error ocurred"))
        }
    }
}

//    get("/user") {
//        var found = false
//        for (user in users) {
//            if (call.parameters["nome"] == user.nome) {
//                call.respond(HttpStatusCode.OK, user)
//                found = true
//            }
//        }
//        if (found == false) {
//            call.respond(HttpStatusCode.OK, users[4])
//        }
//    }
