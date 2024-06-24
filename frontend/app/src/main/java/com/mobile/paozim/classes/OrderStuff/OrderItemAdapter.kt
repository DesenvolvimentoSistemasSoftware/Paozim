package com.mobile.paozim.classes.OrderStuff

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mobile.paozim.R
import com.mobile.paozim.classes.Rate
import com.mobile.paozim.classes.Responses.SimpleResponse
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.retrofit.RateAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

class OrderItemAdapter(
    private val orderID: Int,
    private val context: Context,
    private val orderItems: List<OrderItemCallback>,
    private val status: String
) : RecyclerView.Adapter<OrderItemAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tv_nome_card_review_vh)
        val price: TextView = itemView.findViewById(R.id.tv_unit_price_review_vh)
        val qtd: TextView = itemView.findViewById(R.id.tv_quantity_review_vh)
        val image: ImageView = itemView.findViewById(R.id.iv_item_review_vh)
        val myRate: RatingBar = itemView.findViewById(R.id.ratingBar2)
        val empty: TextView = itemView.findViewById(R.id.tv_empty_review_vh)
        val cant: TextView = itemView.findViewById(R.id.tv_cant_review_vh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentItem = orderItems[position]

        holder.name.text = currentItem.name
        holder.price.text = "R$ %.2f".format(currentItem.price)
        holder.qtd.text = currentItem.quantity.toString()

        val clickable = currentItem.myRate == 6
        if(clickable){
            holder.myRate.visibility = View.GONE
            if(status == "Pendente"){
                holder.cant.visibility = View.VISIBLE
            }
            else{
                holder.empty.visibility = View.VISIBLE
            }
        }
        else{
            holder.myRate.rating = currentItem.myRate.toFloat()
        }

        holder.itemView.setOnClickListener {
            if(clickable){
                showDialogBox().thenAccept { value ->
                    sendReview(value, currentItem.itemID)
                    holder.myRate.rating = value.toFloat()
                    holder.myRate.visibility = View.VISIBLE
                    holder.empty.visibility = View.GONE
                    holder.cant.visibility = View.GONE
                }
            }
        }

        Glide.with(holder.itemView.context)
            .load(currentItem.image)
            .transform(CenterCrop(), RoundedCorners(20))
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }

    private fun showDialogBox(): CompletableFuture<Int> {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_box_review)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val ratingBar : RatingBar = dialog.findViewById(R.id.ratingBar3)
        val send : Button = dialog.findViewById(R.id.btn_send_review)
        val aux = CompletableFuture<Int>()
        var ratingValue = 0

        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            ratingValue = rating.toInt()
        }

        send.setOnClickListener{
            aux.complete(ratingValue)
            dialog.dismiss()
        }

        dialog.show()
        return aux
    }

    private fun sendReview(stars: Int, id: Int){
        val rate = Rate(id, UserInstance.Usuario.email, stars)
        val retIn = RetrofitInstance.getRetrofitInstance().create(RateAPI::class.java)
        retIn.createRate(rate).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(context, "Erro ao enviar review", Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                Toast.makeText(context, "Review enviada", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

