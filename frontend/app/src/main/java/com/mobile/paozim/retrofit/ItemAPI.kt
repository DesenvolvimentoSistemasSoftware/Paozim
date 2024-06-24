package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

const val ITENS = "$API_VERSION/itens"
const val RANDOM_REQUEST = "$ITENS/random"
const val ITEM_REQUEST = "$ITENS/{id}"
const val SELLER_ITEM_REQUEST = "$ITENS/seller/{sellerID}"
const val NAME_ITEM_REQUEST = "$ITENS/name/{name}"
const val ADD_SIGNATURE_REQUEST = "$ITENS/signature"
const val GET_SIGNATURE_REQUEST = "$ITENS/signature/{email}"

interface ItemAPI {
    @Headers("Content-Type: application/json")
    @GET(RANDOM_REQUEST)
    fun getRandomItens(): Call<Item>
    @GET(ITEM_REQUEST)
    fun getItemById(@Header("id") id: String): Call<Item>

    @GET(SELLER_ITEM_REQUEST)
    fun getItemsBySeller(@Header("sellerID") sellerID: String): Call<List<Item>>

    @GET(NAME_ITEM_REQUEST)
    fun getItemsByName(@Header("name") name: String): Call<List<Item>>

    @POST(ADD_SIGNATURE_REQUEST)
    fun addSignature(@Body item: Item): Call<Item>

    @GET(GET_SIGNATURE_REQUEST)
    fun getSignedItems(@Header("email") email: String): Call<List<Item>>
}
