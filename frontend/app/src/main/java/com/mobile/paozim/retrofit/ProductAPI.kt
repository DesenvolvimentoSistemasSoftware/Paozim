package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductAPI {
    @GET("random/product")
    fun getRandomProduct():Call<Product>
}
