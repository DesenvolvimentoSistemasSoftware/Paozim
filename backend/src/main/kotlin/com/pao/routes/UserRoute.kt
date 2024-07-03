package com.pao.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.pao.authentication.JwtService
import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.userStuff.*
import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.server.request.*

const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
const val UPDATE_REQUEST = "$USERS/update"
const val DELETE_REQUEST = "$USERS/delete"
const val CHANGE_NAME_REQUEST = "$USERS/changeName"

fun Route.UserRoute(db:Repo, jwtService:JwtService, hashFunction: (String) -> String){
    post(REGISTER_REQUEST){
        val registerRequest = try {
            call.receive<User>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","Faltam alguns campos"))
            return@post
        }

        try {
            registerRequest.senha = hashFunction(registerRequest.senha)
            db.addUser(registerRequest)
            call.respond(HttpStatusCode.OK,SimpleResponse(success="true",message="Cadastro com sucesso"))
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Algo deu errado"))
            return@post
        }
    }
    post(LOGIN_REQUEST){
        val loginRequest = try {
            call.receive<LoginRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest, UserResponse("false","Faltam alguns campos",null))
            return@post
        }
        try {
            val user = db.findUserByEmail(loginRequest.email)
            if(user == null){
                call.respond(HttpStatusCode.BadRequest, UserResponse("false","Usuário não encontrado",null))
            } else {
                if(user.senha == hashFunction(loginRequest.senha)){
                    call.respond(HttpStatusCode.OK, UserResponse("true","Login com sucesso!",user))
                } else {
                    call.respond(HttpStatusCode.BadRequest, UserResponse("false","Senha incorreta",null))
                }
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict, UserResponse("false",e.message ?: "Algo deu errado",null))
        }
    }
    post(UPDATE_REQUEST){
        val updateRequest = try {
            call.receive<UpdateRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","Missing some fields"))
            return@post
        }

        try {
            val user = db.findUserByEmail(updateRequest.email)
            if(user == null){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
            } else {
                if(user.senha == hashFunction(updateRequest.oldSenha)){
                    updateRequest.newSenha = hashFunction(updateRequest.newSenha)
                    db.updateUser(updateRequest)
                    call.respond(HttpStatusCode.OK,SimpleResponse("true",jwtService.generateToken(updateRequest.email)))
                } else {
                    call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
                }
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "NAO"))
        }
    }
    post(DELETE_REQUEST) {
        val deleteRequest = call.receive<DeleteRequest>()
        try {
            val user = db.findUserByEmail(deleteRequest.email)
            if(user == null){
                call.respond(HttpStatusCode.BadRequest, UserResponse("false","Usuário não encontrado",null))
            } else {
                if(user.senha != hashFunction(deleteRequest.senha)){
                    call.respond(HttpStatusCode.BadRequest, UserResponse("false","Senha incorreta",null))
                }
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict, UserResponse("false",e.message ?: "Algo deu errado",null))
        }

        try {
            db.deleteUser(deleteRequest.email)
            call.respond(HttpStatusCode.OK,SimpleResponse("true","Usuário deletado com sucesso"))
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "Algo deu errado"))
        }
    }
    post(CHANGE_NAME_REQUEST) {
        val updateRequest = try {
            call.receive<ChangeNameRequest>()
        } catch (e: Exception){
            call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","Missing some fields"))
            return@post
        }

        try {
            val user = db.findUserByEmail(updateRequest.email)
            if(user == null){
                call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
            } else {
                if(user.senha == hashFunction(updateRequest.senha)){
                    db.changeUserName(updateRequest.email, updateRequest.newName)
                    call.respond(HttpStatusCode.OK,SimpleResponse("true","Nome atualizado com sucesso"))
                } else {
                    call.respond(HttpStatusCode.BadRequest,SimpleResponse("false","NAO"))
                }
            }
        } catch (e: Exception){
            call.respond(HttpStatusCode.Conflict,SimpleResponse("false",e.message ?: "NAO"))
        }
    }
}
