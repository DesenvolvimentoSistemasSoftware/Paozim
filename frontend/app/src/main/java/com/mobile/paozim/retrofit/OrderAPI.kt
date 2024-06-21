package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.OrderStuff.Order
import com.mobile.paozim.classes.OrderStuff.OrderCallback
import com.mobile.paozim.classes.Responses.SimpleResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

const val ORDER = "$API_VERSION/order"
const val ORDER_REQUEST = "$ORDER/{id}"
const val CREATE_ORDER_REQUEST = "$ORDER/create"
const val LIST_ORDER_REQUEST = "$ORDER/list/{email}"
interface OrderAPI {
    @Headers("Content-Type: application/json")
    @GET(ORDER_REQUEST)
    fun getOrderById(@Header("id") id: String): Call<Order>

    @POST(CREATE_ORDER_REQUEST)
    fun createOrder(@Body order: Order): Call<SimpleResponse>

    @GET(LIST_ORDER_REQUEST)
    fun getOrdersByEmail(@Path("email") email: String): Call<List<OrderCallback>>
}
