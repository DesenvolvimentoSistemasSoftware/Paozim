package com.mobile.paozim.retrofit.API

import com.mobile.paozim.classes.signatureStuff.Signature
import com.mobile.paozim.classes.SimpleResponse
import com.mobile.paozim.retrofit.API_VERSION
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

const val SIGNATURE = "$API_VERSION/signature"
const val ADD_SIGNATURE = "$SIGNATURE/create"
const val LIST_SIGNATURE = "$SIGNATURE/{email}"

interface SignatureAPI {
    @Headers("Content-Type: application/json")
    @POST(ADD_SIGNATURE)
    fun addSignature(@Body req: Signature): Call<SimpleResponse>

    @Headers("Content-Type: application/json")
    @GET(LIST_SIGNATURE)
    fun getSignedItems(@Path("email") email: String): Call<List<Signature>>
}
