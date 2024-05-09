package com.mobile.paozim.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
object ApiClient {
    val apiService: ProductAPI by lazy {
        RetrofitClient.retrofit.create(ProductAPI::class.java)
    }
}

class ApiCall(id:String){

}
