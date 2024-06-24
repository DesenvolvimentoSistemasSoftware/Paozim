package com.mobile.paozim.retrofit

import com.mobile.paozim.classes.Adress
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val ADRESS_REQUEST = "https://viacep.com.br/ws/{cep}/json/"

interface AdressAPI {
     @Headers("Content-Type: application/json")
     @GET(ADRESS_REQUEST)
     fun getAdress(@Path("cep") cep: String): Call<Adress>
}
