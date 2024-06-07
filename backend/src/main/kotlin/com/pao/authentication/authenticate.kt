package com.pao.authentication

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun hash(password:String):String {
    val hashKey = System.getenv("HASH_SECRET_KEY").toByteArray()
    val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")
    val hmac = Mac.getInstance("HmacSHA1")
    hmac.init(hmacKey)
    return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}

fun isEmail(email:String):Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
    return email.matches(emailRegex)
}
