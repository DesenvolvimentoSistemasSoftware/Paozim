package com.mobile.paozim.activities

import SignatureOrder
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobile.paozim.R
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.classes.Item
import com.mobile.paozim.classes.Seller
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityDetailBinding
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import com.mobile.paozim.retrofit.SellerAPI
import com.mobile.paozim.retrofit.SignatureAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.CompletableFuture

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var itemSelected: Item
    private lateinit var seller: Seller
    private val retIn2 = RetrofitInstance.getRetrofitInstance().create(SignatureAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            itemSelected = intent.getParcelableExtra("escolhido",Item::class.java)!!
        else
            itemSelected = intent.getParcelableExtra<Item>("escolhido")!!

        getSeller(itemSelected.sellerID)
        setInformationViews()
        setClickListeners()
    }
    private fun setInformationViews(){
        if(itemSelected.avgRate != 6.0){
            binding.ratingBar.visibility = android.view.View.VISIBLE
            binding.ratingBar.rating = itemSelected.avgRate.toFloat()
        }
        else{
            binding.ratingBar.visibility = android.view.View.GONE
            binding.tvAvaliacaoEmpty.visibility = android.view.View.VISIBLE
        }
        binding.tvName.text = itemSelected.name
        binding.tvDescricao.text = itemSelected.description
        binding.tvPrice.text = "R$ %.2f".format(itemSelected.price)
        Glide.with(this@DetailActivity)
            .load(itemSelected.image)
            .into(binding.ivThumb)

        binding.tvLoja.text = itemSelected.sellerID.toString()
    }
    private fun setClickListeners(){
        binding.ivMenos.setOnClickListener(){
            var qtd = binding.tvQtd.text.toString().toInt()
            if(qtd > 0){
                qtd--
                var total = itemSelected.price * qtd
                binding.tvQtd.text = qtd.toString()
                binding.tvTotal.text = "%.2f".format(total)
            }
        }
        binding.ivMais.setOnClickListener(){
            var qtd = binding.tvQtd.text.toString().toInt()
            if(qtd < itemSelected.stock){
                qtd++
                var total = itemSelected.price * qtd
                binding.tvQtd.text = qtd.toString()
                binding.tvTotal.text = "%.2f".format(total)
            }
        }
        binding.fabBack.setOnClickListener(){
            finish()
        }
        binding.btnAddCarrinho.setOnClickListener() {
            if (binding.tvQtd.text.toString().toInt() != 0){
                if(CartInstance.Carro.storeID != null && CartInstance.Carro.storeID != itemSelected.sellerID.toString()){
                    showDialogBox().thenAccept { result ->
                        if(result){
                            CartInstance.clearCart(this)
                            CartInstance.addItem(
                                this,
                                itemSelected.sellerID.toString(),
                                itemSelected,
                                binding.tvQtd.text.toString().toInt(),
                                0.0
                            )
                            CartInstance.Carro.itens.forEach {
                                Log.d("CART", "Item: ${it.name} - Qtd: ${it.qtd} - Total: ${it.qtd * it.price}")
                            }
                            finish()
                        }
                    }
                }
                else {
                    CartInstance.addItem(
                        this,
                        itemSelected.sellerID.toString(),
                        itemSelected,
                        binding.tvQtd.text.toString().toInt(),
                        0.0
                    )
                    CartInstance.Carro.itens.forEach {
                        Log.d("CART","Item: ${it.name} - Qtd: ${it.qtd} - Total: ${it.qtd * it.price}")
                    }
                    finish()
                }
            }
        }
        binding.btnAddAssinatura.setOnClickListener(){
            val signatureOrder = SignatureOrder(itemSelected.id, UserInstance.Usuario.email)
            retIn2.addSignature(signatureOrder).enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    if (response.body() != null) {
                        Log.d("VEJA", "Adicionado com sucesso")
                        Toast.makeText(this@DetailActivity, "Item assinado com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Log.d("VEJA", "Falhou")
                    }
                }
                override fun onFailure(call: Call<Item>, t: Throwable) {
                    Log.d("VEJA", "Falhou")
                }
            })
        }
        binding.tvLoja.setOnClickListener(){
            val intent = Intent(this, SellerActivity::class.java)
            intent.putExtra("vendedor", seller)
            intent.putExtra("id", itemSelected.sellerID)
            startActivity(intent)
        }
    }
    private fun showDialogBox(): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box_new_cart)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val yesBtn : Button = dialog.findViewById(R.id.btn_accept)
        val noBtn : Button = dialog.findViewById(R.id.btn_cancel)

        yesBtn.setOnClickListener {
            future.complete(true)
            dialog.dismiss()
        }
        noBtn.setOnClickListener {
            future.complete(false)
            dialog.dismiss()
        }

        dialog.show()
        return future
    }
    private fun getSeller(id: Int) {
        val retIn = RetrofitInstance.getRetrofitInstance().create(SellerAPI::class.java)
        retIn.getSeller(id).enqueue(object : Callback<Seller> {
            override fun onResponse(call: Call<Seller>, response: Response<Seller>) {
                seller = response.body()!!
                seller.image = BASE_URL + seller.image
                binding.tvLoja.text = seller.nome
                binding.tvLoja.isClickable = true
            }
            override fun onFailure(call: Call<Seller>, t: Throwable) {
                Log.d("VEJA", "Falhou")
                binding.tvLoja.isClickable = false
            }
        })
    }
}
