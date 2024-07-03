package com.pao.routes

import com.pao.data.classes.SimpleResponse
import com.pao.data.classes.signatureStuff.Signature
import com.pao.plugins.API_VERSION
import com.pao.repositories.Repo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

const val SIGNATURE = "$API_VERSION/signature"
const val ADD_SIGNATURE = "$SIGNATURE/create"
const val LIST_SIGNATURE = "$SIGNATURE/{email}"

fun Route.SignatureRoute(db: Repo){
    post(ADD_SIGNATURE) {
        val signatureOrder = try {
            call.receive<Signature>()
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
    get(LIST_SIGNATURE){
        val email = call.parameters["email"]
        try {
            launch {
                val aux = db.findSignaturesByUser(email!!)
                val signatures : MutableList<Signature> = mutableListOf()
                for(sig in aux) {
                    if(sig.status == "Ativo") {
                        var currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        val currentTimeParsed = LocalDateTime.parse(currentTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        val signatureTime = LocalDateTime.parse(sig.dayStart + " " + sig.arriveTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))

                        if(sig.frequency == "Diário") {
                            val daysDiff = ChronoUnit.DAYS.between(signatureTime, currentTimeParsed)
                            if (daysDiff > 0){
                                sig.currPeriod = daysDiff.toInt()
                            }
                            if(daysDiff >= sig.totalPeriod) {
                                db.updateSignatureStatus(sig.id, "Finalizado")
                            } else {
                                signatures.add(sig)
                            }
                        }
                        else if(sig.frequency == "Semanal") {
                            val weeksDiff = ChronoUnit.WEEKS.between(signatureTime, currentTimeParsed)
                            if (weeksDiff > 0) {
                                sig.currPeriod = weeksDiff.toInt()
                            }
                            if(weeksDiff >= sig.totalPeriod) {
                                db.updateSignatureStatus(sig.id, "Finalizado")
                            } else {
                                signatures.add(sig)
                            }
                        }
                        else { // Mensal
                            val monthsDiff = ChronoUnit.MONTHS.between(signatureTime, currentTimeParsed)
                            if (monthsDiff > 0) {
                                sig.currPeriod = monthsDiff.toInt()
                            }
                            if(monthsDiff >= sig.totalPeriod) {
                                db.updateSignatureStatus(sig.id, "Finalizado")
                            } else {
                                signatures.add(sig)
                            }
                        }
                    }
                }
                call.respond(HttpStatusCode.OK, signatures)
            }.join()
        } catch (e: Exception){
            call.respond(HttpStatusCode.NotFound, SimpleResponse("false","NAO"))
        }
    }
}
