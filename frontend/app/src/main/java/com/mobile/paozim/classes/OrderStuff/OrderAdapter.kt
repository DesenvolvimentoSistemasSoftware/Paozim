package com.mobile.paozim.classes.OrderStuff

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.R
import com.mobile.paozim.databinding.ItemOrderBinding

class OrderAdapter(private val orders: List<OrderCallback>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    class OrderViewHolder(private val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderCallback) {
            binding.orderId.text = "ID do pedido: " + order.id.toString()
            binding.orderPrice.text = order.totalPrice.toString()
            binding.orderArrived.text = order.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentItem = orders[position]
        holder.bind(currentItem)

        Log.d("ItemInfo", currentItem.id.toString())
        Log.d("ItemInfo", currentItem.totalPrice.toString())
        Log.d("ItemInfo", currentItem.status)
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

