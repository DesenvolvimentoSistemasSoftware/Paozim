package com.pao.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.pao.data.classes.userStuff.User

class JwtService {

    private val issuer = "paoServer"
    private val jwtSecret = "database"
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(email: String): String {
        return JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("email", email)
            .sign(algorithm)
    }

    // Retorna o verifier
    fun getVerifier(): JWTVerifier {
        return verifier
    }
}
