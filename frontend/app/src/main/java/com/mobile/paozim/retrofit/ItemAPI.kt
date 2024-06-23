package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val ITENS = "$API_VERSION/itens"
const val RANDOM_REQUEST = "$ITENS/random"
const val ITEM_REQUEST = "$ITENS/{id}"
const val CATEGORY_ITEM_REQUEST = "$ITENS/category/{category}"
const val SELLER_ITEM_REQUEST = "$ITENS/seller/{sellerID}"
const val NAME_ITEM_REQUEST = "$ITENS/name/{name}"

interface ItemAPI {
    @Headers("Content-Type: application/json")
    @GET(RANDOM_REQUEST)
    fun getRandomItem(): Call<Item>

    @Headers("Content-Type: application/json")
    @GET(SELLER_ITEM_REQUEST)
    fun getItemsBySeller(@Path("sellerID") sellerID: Int): Call<List<Item>>

    @Headers("Content-Type: application/json")
    @GET(NAME_ITEM_REQUEST)
    fun getItemsByName(@Path("name") name: String): Call<List<Item>>

    @Headers("Content-Type: application/json")
    @GET(CATEGORY_ITEM_REQUEST)
    fun getItemsByCategory(@Path("category") category: String): Call<List<Item>>
}
