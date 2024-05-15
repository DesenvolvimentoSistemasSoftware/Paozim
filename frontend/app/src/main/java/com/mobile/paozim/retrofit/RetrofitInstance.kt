package com.mobile.paozim.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://c1fd-2804-14c-71-4054-8c62-3161-9d38-dcae.ngrok-free.app"

object RetrofitInstance {
    val api:ProductAPI by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductAPI::class.java)
    }
}
