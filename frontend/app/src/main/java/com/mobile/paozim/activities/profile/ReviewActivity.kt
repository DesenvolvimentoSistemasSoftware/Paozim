package com.mobile.paozim.activities.profile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.paozim.classes.orderStuff.OrderItemAdapter
import com.mobile.paozim.classes.orderStuff.OrderItemCallback
import com.mobile.paozim.databinding.ActivityReviewBinding
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.API.OrderAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : ComponentActivity() {
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orderID = intent.getIntExtra("orderID", -1)
        val status = intent.getStringExtra("status")

        binding = ActivityReviewBinding.inflate(layoutInflater)
        binding.tvOrderNumberReview.text = orderID.toString()
        binding.ivBackReview.setOnClickListener {
            finish()
        }
        setContentView(binding.root)
        setReviews(orderID, status!!)
    }

    private fun setReviews(orderID: Int, status: String) {
        if(orderID == -1) return
        binding.pbWhileSearchingReview.visibility = android.view.View.VISIBLE
        val retIn = RetrofitInstance.getRetrofitInstance().create(OrderAPI::class.java)
        retIn.getOrderById(orderID.toString()).enqueue(object : Callback<List<OrderItemCallback>> {
            override fun onFailure(call: Call<List<OrderItemCallback>>, t: Throwable) {
                Log.d("VEJA", "Falhou")
                binding.pbWhileSearchingReview.visibility = android.view.View.GONE
            }
            override fun onResponse(call: Call<List<OrderItemCallback>>, response: Response<List<OrderItemCallback>>) {
                val orderList = response.body()
                if(!orderList.isNullOrEmpty()){
                    for (item in orderList){
                        item.image = BASE_URL + item.image
                        Log.d("VEJA", "id: ${item.itemID} e nome: ${item.name}")
                    }
                    binding.rvReview.layoutManager = LinearLayoutManager(this@ReviewActivity)
                    binding.rvReview.adapter = OrderItemAdapter(orderID,this@ReviewActivity,orderList, status)
                    binding.rvReview.visibility = android.view.View.VISIBLE
                    binding.pbWhileSearchingReview.visibility = android.view.View.GONE
                }
                else{
                    binding.pbWhileSearchingReview.visibility = android.view.View.GONE
                }
            }
        })
    }
}
