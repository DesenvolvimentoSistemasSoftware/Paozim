package com.mobile.paozim.classes.itemStuff

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.mobile.paozim.R
import com.mobile.paozim.activities.DetailActivity

class ItemAdapter (
    var itens: List<Item>,
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.tv_title_search_vh)
        val image:ImageView = itemView.findViewById(R.id.iv_search_vh)
        val price:TextView = itemView.findViewById(R.id.tv_price_search_vh)
        val star:ImageView = itemView.findViewById(R.id.iv_star_search_vh)
        val avg:TextView = itemView.findViewById(R.id.tv_rating_search_vh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itens[position]
        holder.name.text = currentItem.name
        holder.price.text = "R$ %.2f".format(currentItem.price)
        if(currentItem.avgRate != 6.0){
            holder.star.visibility = View.VISIBLE
            holder.avg.visibility = View.VISIBLE
            holder.avg.text = "%.1f".format(currentItem.avgRate)
        }

        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .transform(CenterCrop())
            .into(holder.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("escolhido", currentItem)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itens.size
    }
}
