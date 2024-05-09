package com.mobile.paozim.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.paozim.classes.Product
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private var randomProductLiveData = MutableLiveData<Product>()

    fun getRandomProduct(){
        RetrofitInstance.api.getRandomProduct().enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.body() != null){
                    val randomProduct: Product = response.body()!!
                    randomProductLiveData.value = randomProduct
                    Log.d("VEJA", "id: ${randomProduct.id} e nome: ${randomProduct.nome}")
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
        })
    }
    fun observeRandomProductLiveData():LiveData<Product>{
        return randomProductLiveData
    }
}
