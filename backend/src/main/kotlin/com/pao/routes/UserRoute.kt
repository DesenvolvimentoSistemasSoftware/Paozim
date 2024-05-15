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
import com.pao.repositories.Repo
import io.ktor.server.request.*

const val API_VERSION = "/v1"
const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"

fun Route.UserRoutes(db:Repo, jwtService:JwtService, hashFunction: (String) -> String){
    post(REGISTER_REQUEST){
        val registerRequest = try {
            call.receive<RegisterRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","Missing some fields"))
            return@post
        }

        try {
            val newUser = User(
                nome = registerRequest.nome,
                senha = hashFunction(registerRequest.senha),
                email = registerRequest.email
            )
            db.addUser(newUser)
            call.respond(HttpStatusCode.OK,SimpleResponse(success="true",message=jwtService.generateToken(newUser)))
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Some error ocurred"))
            return@post
        }
    }
    post(LOGIN_REQUEST){
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
            return@post
        }
        try {
            val user = db.findUserByEmail(loginRequest.email)
            if(user == null){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
            } else {
                if(user.senha == hashFunction(loginRequest.senha)){
                    call.respond(HttpStatusCode.OK,SimpleResponse("true",jwtService.generateToken(user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
                }
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "NAO"))
        }
    }
}
