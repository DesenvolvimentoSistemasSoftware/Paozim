package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Rate
import com.mobile.paozim.classes.Responses.SimpleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val RATE = "$API_VERSION/rate"
const val RATE_CREATE = "$RATE/create"

interface RateAPI {
    @Headers("Content-Type: application/json")
    @POST(RATE_CREATE)
    fun createRate(@Body rate: Rate): Call<SimpleResponse>
}
