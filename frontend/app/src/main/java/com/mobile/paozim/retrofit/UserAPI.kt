package com.mobile.paozim.retrofit

import com.mobile.paozim.data.remote.models.SimpleResponse
import com.mobile.paozim.data.remote.models.UserF
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserAPI {
    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/register")
    fun register(@Body user:UserF): Call<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/login")
    fun login(@Body user:UserF): Call<SimpleResponse>
}
