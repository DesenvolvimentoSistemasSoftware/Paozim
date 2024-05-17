package com.mobile.paozim.classes.CartStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobile.paozim.R

class CartAdapter (
    var itens: List<CartItem>
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.tv_nome_card)
        val image:ImageView = itemView.findViewById(R.id.iv_image_item)
        val price:TextView = itemView.findViewById(R.id.tv_unit_price)
        val qtd:TextView = itemView.findViewById(R.id.tv_qtd_card)
        val total:TextView = itemView.findViewById(R.id.tv_total_item)
        val add:ImageView = itemView.findViewById(R.id.iv_mais_card)
        val minus:ImageView = itemView.findViewById(R.id.iv_menos_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = itens[position]
        holder.name.text = currentItem.name
        holder.qtd.text = currentItem.qtd.toString()
        holder.price.text = "R$ %.2f".format(currentItem.price)
        holder.total.text = "%.2f".format(currentItem.price * currentItem.qtd)
        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .into(holder.image)

        holder.add.setOnClickListener {
            currentItem.qtd++
            holder.qtd.text = currentItem.qtd.toString()
            holder.total.text = "%.2f".format(currentItem.price * currentItem.qtd)
        }
        holder.minus.setOnClickListener {
            if (currentItem.qtd > 1) {
                currentItem.qtd--
                holder.qtd.text = currentItem.qtd.toString()
                holder.total.text = "%.2f".format(currentItem.price * currentItem.qtd)
            }
        }
    }

    override fun getItemCount(): Int {
        return itens.size
    }

}
