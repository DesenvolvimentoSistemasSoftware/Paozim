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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        binding.ivBackHistory.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
        setOrders()
    }

    private fun setOrders(){
        binding.pbWhileSearchingHistory.visibility = android.view.View.VISIBLE
        val retIn = RetrofitInstance.getRetrofitInstance().create(OrderAPI::class.java)
        retIn.getOrdersByEmail(UserInstance.Usuario.email).enqueue(object : Callback<List<OrderCallback>> {
            override fun onFailure(call: Call<List<OrderCallback>>, t: Throwable) {
                Log.d("VEJA", "Falhou")
                binding.pbWhileSearchingHistory.visibility = android.view.View.GONE
                binding.tvEmptyHistory.visibility = android.view.View.VISIBLE
            }
            override fun onResponse(call: Call<List<OrderCallback>>, response: Response<List<OrderCallback>>) {
                val orderList = response.body()
                if(!orderList.isNullOrEmpty()){
                    binding.rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    binding.rvHistory.adapter = OrderAdapter(orderList)
                    binding.rvHistory.visibility = android.view.View.VISIBLE
                    binding.pbWhileSearchingHistory.visibility = android.view.View.GONE
                }
                else{
                    binding.pbWhileSearchingHistory.visibility = android.view.View.GONE
                    binding.tvEmptyHistory.visibility = android.view.View.VISIBLE
                }
            }
        })
    }

}

