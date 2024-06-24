package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.UserStuff.ChangeNameRequest
import com.mobile.paozim.classes.UserStuff.LoginRequest
import com.mobile.paozim.classes.Responses.SimpleResponse
import com.mobile.paozim.classes.UserStuff.User
import com.mobile.paozim.classes.Responses.UserResponse
import com.mobile.paozim.classes.UserStuff.DeleteRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val USERS = "$API_VERSION/users"
const val REGISTER_REQUEST = "$USERS/register"
const val LOGIN_REQUEST = "$USERS/login"
const val CHANGE_NAME_REQUEST = "$USERS/changeName"
const val DELETE_REQUEST = "$USERS/delete"

interface UserAPI {
    @Headers("Content-Type: application/json")
    @POST(REGISTER_REQUEST)
    fun register(@Body user: User): Call<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST(LOGIN_REQUEST)
    fun login(@Body user: LoginRequest): Call<UserResponse>

    // Change name
    @Headers("Content-Type: application/json")
    @POST(CHANGE_NAME_REQUEST)
    fun changeName(@Body user: ChangeNameRequest): Call<SimpleResponse>

    @Headers("Content-Type: application/json")
    @POST(DELETE_REQUEST)
    fun deleteUser(@Body user: DeleteRequest): Call<Void>
}
