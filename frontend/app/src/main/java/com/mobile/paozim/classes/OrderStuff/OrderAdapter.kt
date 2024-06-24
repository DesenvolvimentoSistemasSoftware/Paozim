package com.mobile.paozim.classes.OrderStuff

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.R
import com.mobile.paozim.activities.DetailActivity
import com.mobile.paozim.activities.profile.ReviewActivity
import com.mobile.paozim.databinding.ItemOrderBinding

class OrderAdapter(
    private val orders: List<OrderCallback>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id:TextView = itemView.findViewById(R.id.tv_id_order_vh)
        val status:TextView = itemView.findViewById(R.id.tv_status_order_vh)
        val timeStart:TextView = itemView.findViewById(R.id.tv_time_start_order_vh)
        val sellerID:TextView = itemView.findViewById(R.id.tv_seller_name_order_vh)
        val subtotal:TextView = itemView.findViewById(R.id.tv_subtotal_order_vh)
        val shipping:TextView = itemView.findViewById(R.id.tv_shipping_order_vh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val currentOrder = orders[position]
        holder.id.text = currentOrder.id.toString()
        holder.timeStart.text = currentOrder.timeStart
        holder.sellerID.text = currentOrder.sellerID.toString()
        holder.subtotal.text = "%.2f".format(currentOrder.totalPrice)
        holder.shipping.text = "%.2f".format(currentOrder.shippingPrice)
        holder.status.text = currentOrder.status
        if(currentOrder.status == "Pendente"){
            holder.status.setTextColor(Color.parseColor("#DAA403"))
        }
        else{
            holder.status.setTextColor(Color.parseColor("#4CAF50"))
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
            intent.putExtra("orderID", currentOrder.id)
            intent.putExtra("status", currentOrder.status)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }
}

