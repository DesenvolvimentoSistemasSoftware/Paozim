package com.mobile.paozim.retrofit.API

import com.mobile.paozim.classes.userStuff.ChangeNameRequest
import com.mobile.paozim.classes.userStuff.LoginRequest
import com.mobile.paozim.classes.SimpleResponse
import com.mobile.paozim.classes.userStuff.User
import com.mobile.paozim.classes.userStuff.UserResponse
import com.mobile.paozim.retrofit.API_VERSION
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
const val CHANGE_NAME_REQUEST = "$USERS/changeName"

interface UserAPI {
    @Headers("Content-Type: application/json")
    @POST(REGISTER_REQUEST)
    fun register(@Body user: User): Call<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST(LOGIN_REQUEST)
    fun login(@Body user: LoginRequest): Call<UserResponse>

    @Headers("Content-Type: application/json")
    @POST(CHANGE_NAME_REQUEST)
    fun changeName(@Body user: ChangeNameRequest): Call<SimpleResponse>
}
