package com.mobile.paozim.classes.OrderStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.R

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

