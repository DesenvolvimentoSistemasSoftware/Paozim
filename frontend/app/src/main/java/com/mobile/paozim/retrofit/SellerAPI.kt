package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Seller
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val SELLER = "$API_VERSION/seller"
const val SELLER_REQUEST = "$SELLER/{id}"

interface SellerAPI {
    @Headers("Content-Type: application/json")
    @GET(SELLER_REQUEST)
    fun getSeller(@Path("id") id: Int): Call<Seller>
}
