package com.mobile.paozim.retrofit

import SignatureOrder
import com.mobile.paozim.classes.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

const val SIGNATURE = "$API_VERSION/signature"
const val ADD_SIGNATURE = "$SIGNATURE/create"
const val GET_SIGNATURE = "$SIGNATURE/{email}"

interface SignatureAPI {
    @Headers("Content-Type: application/json")
    @POST(ADD_SIGNATURE)
    fun addSignature(@Body req: SignatureOrder): Call<Item>

    @Headers("Content-Type: application/json")
    @GET(GET_SIGNATURE)
    fun getSignedItems(@Path("email") email: String): Call<List<Item>>
}
