package com.mobile.paozim.classes.CategoryStuff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.R

class CategoryAdapter (
    private val categories: List<Category>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.tv_category_name_vh)
        val image:ImageView = itemView.findViewById(R.id.iv_category_image_vh)
    }

    interface OnItemClickListener {
        fun onItemClick(category: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categories[position]
        val resId: Int = holder.image.context.resources.getIdentifier(currentCategory.image, "drawable", holder.image.context.packageName)
        holder.name.text = currentCategory.name
        holder.image.setImageResource(resId)
        holder.itemView.setOnClickListener {
            listener.onItemClick(currentCategory)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
