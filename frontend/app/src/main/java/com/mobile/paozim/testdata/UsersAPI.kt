package com.mobile.paozim.testdata

import retrofit2.Call
import retrofit2.http.GET

interface UsersAPI {
    @GET("/user/pedro")
    fun getUserByName(): Call<List<UserInfo>>

//  ######### TESTE COM TUTORIAL #########
//    @GET("/posts")
//    fun getUserByName(): Call<List<UserInfo>>
//  ######### TESTE COM TUTORIAL #########
}
