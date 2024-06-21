package com.mobile.paozim.activities.profile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.paozim.classes.OrderStuff.OrderAdapter
import com.mobile.paozim.classes.OrderStuff.OrderCallback
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityHistoryBinding
import com.mobile.paozim.retrofit.OrderAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : ComponentActivity() {
    private lateinit var binding: ActivityHistoryBinding
    val retIn = RetrofitInstance.getRetrofitInstance().create(OrderAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ao renderizar a página, obter o histórico de pedidos do usuário logado e mostrar cada pedido no recyclerview de ID iv_product_card
        getHistory(UserInstance.Usuario.email, object : OrdersCallback {
            override fun onSuccess(orders: List<OrderCallback>) {
                binding.ivProductCard.adapter = OrderAdapter(orders)
                binding.ivProductCard.layoutManager = LinearLayoutManager(this@HistoryActivity)
            }

            override fun onFailure(t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
        })
    }

    interface OrdersCallback {
        fun onSuccess(orders: List<OrderCallback>)
        fun onFailure(t: Throwable)
    }

    fun getHistory(email: String, callback: OrdersCallback) {
        retIn.getOrdersByEmail(email).enqueue(object : Callback<List<OrderCallback>> {
            override fun onResponse(call: Call<List<OrderCallback>>, response: Response<List<OrderCallback>>) {
                if (response.body() != null) {
                    val orders: List<OrderCallback> = response.body()!!
                    callback.onSuccess(orders)
                } else {
                    callback.onFailure(Throwable("No orders found"))
                }
            }

            override fun onFailure(call: Call<List<OrderCallback>>, t: Throwable) {
                Log.d("VEJA", "Falhou")
                callback.onFailure(t)
            }
        })
    }

}

