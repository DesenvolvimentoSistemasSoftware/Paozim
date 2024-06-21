package com.mobile.paozim.activities.profile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.mobile.paozim.classes.Order
import com.mobile.paozim.databinding.ActivityHistoryBinding
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.OrderAPI
import com.mobile.paozim.retrofit.RetroInsta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : ComponentActivity() {
    private lateinit var binding: ActivityHistoryBinding
    val retIn = RetroInsta.getRetrofitInstance().create(OrderAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        binding.ivProductCard.setOnClickListener {
            // ivProductCard.text deve conter o conteúdo do pedido
            getHistory()
        }
    }

    fun getHistory() {
        retIn.getOrdersByEmail("email").enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.body() != null) {
                    var orders: List<Order> = response.body()!!
                    println(orders)
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
        })
    }
}

