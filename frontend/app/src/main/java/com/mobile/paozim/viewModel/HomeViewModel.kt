package com.mobile.paozim.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.paozim.classes.Item
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private var randomItemLiveData = MutableLiveData<Item>()
    val retIn = RetrofitInstance.getRetrofitInstance().create(ItemAPI::class.java)

    fun getRandomItem(){
        retIn.getRandomItens().enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if(response.body() != null){
                    var randomItem: Item = response.body()!!
                    randomItem.image = BASE_URL + randomItem.image
                    randomItemLiveData.value = randomItem
                    Log.d("VEJA", "id: ${randomItem.id} e nome: ${randomItem.name}")
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
        })
    }
    fun observeRandomItemLiveData():LiveData<Item>{
        return randomItemLiveData
    }
}
