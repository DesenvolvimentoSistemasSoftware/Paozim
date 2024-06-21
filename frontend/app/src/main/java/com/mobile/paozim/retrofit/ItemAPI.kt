package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

const val ITENS = "$API_VERSION/itens"
const val RANDOM_REQUEST = "$ITENS/random"
const val ITEM_REQUEST = "$ITENS/{id}"
const val SELLER_ITEM_REQUEST = "$ITENS/seller/{sellerID}"
const val NAME_ITEM_REQUEST = "$ITENS/name/{name}"

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
}
