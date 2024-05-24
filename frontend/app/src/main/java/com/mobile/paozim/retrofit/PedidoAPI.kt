//package com.mobile.paozim.retrofit
//
//import com.mobile.paozim.data.remote.models.Pedido
//import com.mobile.paozim.data.remote.models.SimpleResponse
//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.GET
//import retrofit2.http.Header
//import retrofit2.http.Headers
//import retrofit2.http.POST
//
//interface PedidoAPI {
//    @Headers("Content-Type: application/json")
//    @POST("$API_VERSION/pedido/create")
//    suspend fun createPedido(
//        @Header("Authorization") token: String,
//        @Body pedido: Pedido
//    ): Call<SimpleResponse>
//
//    @Headers("Content-Type: application/json")
//    @GET("$API_VERSION/pedido")
//    suspend fun getAllPedidos(
//        @Header("Authorization") token: String
//    ): Call<List<Pedido>>
//
//    @Headers("Content-Type: application/json")
//    @POST("$API_VERSION/pedido/update")
//    suspend fun updateStatus(
//        @Header("Authorization") token: String,
//        @Body pedido: Pedido
//    ): Call<SimpleResponse>
//}
