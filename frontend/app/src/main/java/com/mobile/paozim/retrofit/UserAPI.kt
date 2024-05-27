package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.UserStuff.LoginRequest
import com.mobile.paozim.data.remote.models.SimpleResponse
import com.mobile.paozim.classes.UserStuff.User
import com.mobile.paozim.data.remote.models.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
interface UserAPI {
    @Headers("Content-Type: application/json")
    @POST(REGISTER_REQUEST)
    fun register(@Body user: User): Call<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST(LOGIN_REQUEST)
    fun login(@Body user: LoginRequest): Call<UserResponse>
}
