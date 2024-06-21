package com.mobile.paozim.activities.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.R
import com.mobile.paozim.classes.OrderStuff.OrderCallback
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityHistoryBinding
import com.mobile.paozim.retrofit.OrderAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderAdapter(private val orders: List<OrderCallback>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val orderId: TextView = itemView.findViewById(R.id.order_id)
        private val orderPrice: TextView = itemView.findViewById(R.id.order_price)
        private val orderArrived: TextView = itemView.findViewById(R.id.order_arrived)

        fun bind(order: OrderCallback) {
            orderId.text = order.id.toString()
            orderPrice.text = order.totalPrice.toString()
            orderArrived.text = order.status
        }
    }
}


class HistoryActivity : ComponentActivity() {
    private lateinit var binding: ActivityHistoryBinding
    val retIn = RetrofitInstance.getRetrofitInstance().create(OrderAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)

        if (UserInstance.logged) {
            // Ao renderizar a página, obter o histórico de pedidos do usuário logado e mostrar cada pedido no recyclerview de ID iv_product_card
            getHistory(UserInstance.Usuario.email, object : OrdersCallback {
                override fun onSuccess(orders: List<OrderCallback>) {
                    binding.ivProductCard.adapter = OrderAdapter(orders)
                    println(orders)
                }

                override fun onFailure(t: Throwable) {
                    Log.d("VEJA", "Falhou")
                }
            })
        }
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

