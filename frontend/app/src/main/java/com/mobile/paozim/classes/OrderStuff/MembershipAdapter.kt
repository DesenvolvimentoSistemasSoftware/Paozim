package com.mobile.paozim.classes.OrderStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mobile.paozim.R
import com.mobile.paozim.classes.Item
import com.mobile.paozim.classes.SignaturedItem


class MembershipAdapter (
    var itens: List<Item>,
    private val cartViewModel: MembershipViewModel
) : RecyclerView.Adapter<MembershipAdapter.MembershipViewHolder>() {

    inner class MembershipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.tv_nome_card)
        val image:ImageView = itemView.findViewById(R.id.iv_image_item)
        val price:TextView = itemView.findViewById(R.id.tv_unit_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembershipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_cart, parent, false)
        return MembershipViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembershipViewHolder, position: Int) {
        val currentItem = itens[position]
        // TODO - definir itens exibidos no item assinado
        holder.name.text = currentItem.name
        holder.price.text = "R$ %.2f".format(currentItem.price)

        Glide.with(holder.image)
            .load(currentItem.image)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return itens.size
    }
}
