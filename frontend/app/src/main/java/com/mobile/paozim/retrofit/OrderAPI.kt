package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.OrderStuff.Order
import com.mobile.paozim.classes.Responses.SimpleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val ORDER = "$API_VERSION/order"
const val ORDER_REQUEST = "$ORDER/{id}"
const val CREATE_ORDER_REQUEST = "$ORDER/create"
const val LIST_ORDER_REQUEST = "$ORDER/list/{email}"
interface OrderAPI {
    @Headers("Content-Type: application/json")
    @POST(CREATE_ORDER_REQUEST)
    fun createOrder(@Body order: Order): Call<SimpleResponse>
}
