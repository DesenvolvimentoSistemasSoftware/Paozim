package com.mobile.paozim.activities.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mobile.paozim.databinding.ActivityHistoryBinding
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.RetroInsta

class HistoryActivity : ComponentActivity() {
    private lateinit var binding: ActivityHistoryBinding
    val retIn = RetroInsta.getRetrofitInstance().create(ItemAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        binding.ivProductCard.setOnClickListener {
            // ivProductCard.text deve conter o conteúdo do pedido

        }
    }

    fun getHistory() {
        retIn.getHistory().enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if(response.body() != null){
                    var order: Order = response.body()!!
                    // order deve conter o histórico de pedidos
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<Order>, t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
        })
    }
}

