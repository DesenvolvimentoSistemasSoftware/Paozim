package com.mobile.paozim.testdata

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        //link obtido ao executar o ngrok
        private const val BASE_URL = "https://4e47-143-107-45-1.ngrok-free.app"
//        private const val BASE_URL = "https://jsonplaceholder.typicode.com"
        private lateinit var INSTANCE: Retrofit

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()
            if(!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }
        fun createUserService(): UsersAPI {
            return getRetrofitInstance().create(UsersAPI::class.java)
        }
    }
}
