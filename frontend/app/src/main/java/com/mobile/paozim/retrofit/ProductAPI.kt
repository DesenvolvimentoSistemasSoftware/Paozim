package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("product/random")
    fun getRandomProduct():Call<Product>

    @GET("product/{id}")
    fun getProduct(@Path("id") id : String):Call<Product>
}
