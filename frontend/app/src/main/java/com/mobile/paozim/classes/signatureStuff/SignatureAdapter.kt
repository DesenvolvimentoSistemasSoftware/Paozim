package com.mobile.paozim.classes.signatureStuff

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.mobile.paozim.R
import com.mobile.paozim.classes.itemStuff.Item
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.API.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignatureAdapter (
    var signatures: List<Signature>,
) : RecyclerView.Adapter<SignatureAdapter.MembershipViewHolder>() {

    inner class MembershipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val constrainDays:ConstraintLayout = itemView.findViewById(R.id.cl_days)
        val day1: View = itemView.findViewById(R.id.v_day1)
        val connectorD1: View = itemView.findViewById(R.id.v_connector1_d)
        val day2: View = itemView.findViewById(R.id.v_day2)
        val connectorD2: View = itemView.findViewById(R.id.v_connector2_d)
        val day3: View = itemView.findViewById(R.id.v_day3)
        val connectorD3: View = itemView.findViewById(R.id.v_connector3_d)
        val day4: View = itemView.findViewById(R.id.v_day4)
        val connectorD4: View = itemView.findViewById(R.id.v_connector4_d)
        val day5: View = itemView.findViewById(R.id.v_day5)
        val connectorD5: View = itemView.findViewById(R.id.v_connector5_d)
        val day6: View = itemView.findViewById(R.id.v_day6)

        val constrainWeeks:ConstraintLayout = itemView.findViewById(R.id.cl_weeks)
        val week1: View = itemView.findViewById(R.id.v_week1)
        val connectorW1: View = itemView.findViewById(R.id.v_connector1_w)
        val week2: View = itemView.findViewById(R.id.v_week2)
        val connectorW2: View = itemView.findViewById(R.id.v_connector2_w)
        val week3: View = itemView.findViewById(R.id.v_week3)

        val constrainMonths:ConstraintLayout = itemView.findViewById(R.id.cl_months)
        val month1: View = itemView.findViewById(R.id.v_month1)
        val connectorM1: View = itemView.findViewById(R.id.v_connector1_m)
        val month2: View = itemView.findViewById(R.id.v_month2)

        val passed:TextView = itemView.findViewById(R.id.tv_passed)

        val id:TextView = itemView.findViewById(R.id.tv_id_membership_vh)
        val qtd:TextView = itemView.findViewById(R.id.tv_quantity_membership_vh)
        val time:TextView = itemView.findViewById(R.id.tv_delivery_membership_vh)
        val startDay:TextView = itemView.findViewById(R.id.tv_day_start_membership_vh)

        val price:TextView = itemView.findViewById(R.id.tv_unit_price_membership_vh)
        val name:TextView = itemView.findViewById(R.id.tv_nome_card_membership_vh)
        val image:ImageView = itemView.findViewById(R.id.iv_image_item_membership_vh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembershipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_membership, parent, false)
        return MembershipViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembershipViewHolder, position: Int) {
        val currentSig = signatures[position]

        holder.id.text = currentSig.id.toString()
        holder.qtd.text = currentSig.quantity.toString()
        holder.time.text = currentSig.arriveTime
        holder.startDay.text = currentSig.dayStart

        getItem(currentSig.itemID, holder)

        if(currentSig.frequency == "Diário") {
            setDays(holder, currentSig.currPeriod, currentSig.totalPeriod)
        } else if(currentSig.frequency == "Semanal") {
            setWeeks(holder, currentSig.currPeriod, currentSig.totalPeriod)
        } else if(currentSig.frequency == "Mensal"){
            setMonths(holder, currentSig.currPeriod, currentSig.totalPeriod)
        }
    }

    private fun setDays(holder: MembershipViewHolder, currPeriod:Int, totalPeriod:Int){
        holder.passed.text = "%d / %d dias".format(currPeriod, totalPeriod)
        holder.constrainDays.visibility = View.VISIBLE

        when(currPeriod%7){
            1 -> {
                holder.day1.setBackgroundResource(R.drawable.green_circle)
            }
            2 -> {
                holder.day1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD1.setBackgroundResource(R.color.light_green)
                holder.day2.setBackgroundResource(R.drawable.green_circle)
            }
            3 -> {
                holder.day1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD1.setBackgroundResource(R.color.light_green)
                holder.day2.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD2.setBackgroundResource(R.color.light_green)
                holder.day3.setBackgroundResource(R.drawable.green_circle)
            }
            4 -> {
                holder.day1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD1.setBackgroundResource(R.color.light_green)
                holder.day2.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD2.setBackgroundResource(R.color.light_green)
                holder.day3.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD3.setBackgroundResource(R.color.light_green)
                holder.day4.setBackgroundResource(R.drawable.green_circle)
            }
            5 -> {
                holder.day1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD1.setBackgroundResource(R.color.light_green)
                holder.day2.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD2.setBackgroundResource(R.color.light_green)
                holder.day3.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD3.setBackgroundResource(R.color.light_green)
                holder.day4.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD4.setBackgroundResource(R.color.light_green)
                holder.day5.setBackgroundResource(R.drawable.green_circle)
            }
            6 -> {
                holder.day1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD1.setBackgroundResource(R.color.light_green)
                holder.day2.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD2.setBackgroundResource(R.color.light_green)
                holder.day3.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD3.setBackgroundResource(R.color.light_green)
                holder.day4.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD4.setBackgroundResource(R.color.light_green)
                holder.day5.setBackgroundResource(R.drawable.green_circle)
                holder.connectorD5.setBackgroundResource(R.color.light_green)
                holder.day6.setBackgroundResource(R.drawable.green_circle)
            }
        }
    }
    private fun setWeeks(holder: MembershipViewHolder, currPeriod:Int, totalPeriod:Int){
        holder.passed.text = "%d / %d semanas".format(currPeriod, totalPeriod)
        holder.constrainWeeks.visibility = View.VISIBLE

        when(currPeriod%4){
            1 -> {
                holder.week1.setBackgroundResource(R.drawable.green_circle)
            }
            2 -> {
                holder.week1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorW1.setBackgroundResource(R.color.light_green)
                holder.week2.setBackgroundResource(R.drawable.green_circle)
            }
            3 -> {
                holder.week1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorW1.setBackgroundResource(R.color.light_green)
                holder.week2.setBackgroundResource(R.drawable.green_circle)
                holder.connectorW2.setBackgroundResource(R.color.light_green)
                holder.week3.setBackgroundResource(R.drawable.green_circle)
            }
        }
    }
    private fun setMonths(holder: MembershipViewHolder, currPeriod:Int, totalPeriod:Int){
        holder.passed.text = "%d / %d meses".format(currPeriod, totalPeriod)
        holder.constrainMonths.visibility = View.VISIBLE

        when(currPeriod%3){
            1 -> {
                holder.month1.setBackgroundResource(R.drawable.green_circle)
            }
            2 -> {
                holder.month1.setBackgroundResource(R.drawable.green_circle)
                holder.connectorM1.setBackgroundResource(R.color.light_green)
                holder.month2.setBackgroundResource(R.drawable.green_circle)
            }
        }
    }

    private fun getItem(id: Int, holder: MembershipViewHolder){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ItemAPI::class.java)
        retIn.getItemByID(id).enqueue(object : Callback<Item> {
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                val item = response.body()!!
                item.image = BASE_URL + item.image

                holder.price.text = "R$ %.2f".format(item.price)
                holder.name.text = item.name
                Glide.with(holder.itemView.context)
                    .load(item.image)
                    .transform(CenterCrop(), RoundedCorners(16))
                    .into(holder.image)
            }
        })
    }

    override fun getItemCount(): Int {
        return signatures.size
    }
}
