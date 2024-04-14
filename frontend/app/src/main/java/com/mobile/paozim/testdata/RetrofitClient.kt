package com.mobile.paozim.testdata

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL = "http://192.168.0.135:8080"
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit {
            if(retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun createUserService(): UsersAPI {
            return getClient().create(UsersAPI::class.java)
        }

//        private lateinit var INSTANCE: Retrofit
////        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
//
//        private fun getRetrofitInstance(): Retrofit {
//            val http = OkHttpClient.Builder()
//            if(!::INSTANCE.isInitialized) {
//                INSTANCE = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(http.build())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//            }
//            return INSTANCE
//        }
//
//        fun createUserService(): UsersAPI {
//            return getRetrofitInstance().create(UsersAPI::class.java)
//        }
    }
}
