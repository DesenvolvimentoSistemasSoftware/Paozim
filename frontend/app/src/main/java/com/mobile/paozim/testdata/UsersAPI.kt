package com.mobile.paozim.testdata

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UsersAPI {
    @GET("/user/pedro")
    fun getUserByName(): Call<UserInfo>

//    @GET("/user")
//    fun getUserByName(@Query("name") name: String): Call<UserInfo>
//    fun get_him(): Response<List<UserInfo>>
//    fun get_him(): Call<UserInfo>

//    @GET("posts")
}
