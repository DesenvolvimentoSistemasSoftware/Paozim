package com.mobile.paozim.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://124c-143-107-45-1.ngrok-free.app"

object RetrofitInstance {
    val api:ProductAPI by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
    }
}
