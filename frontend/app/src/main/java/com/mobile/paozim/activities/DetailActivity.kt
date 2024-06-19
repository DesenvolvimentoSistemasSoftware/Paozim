package com.mobile.paozim.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobile.paozim.R
import com.mobile.paozim.classes.Product
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.classes.Item
import com.mobile.paozim.databinding.ActivityDetailBinding
import java.util.concurrent.CompletableFuture

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var prodSelec: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            prodSelec = intent.getParcelableExtra("escolhido",Item::class.java)!!
        else
            prodSelec = intent.getParcelableExtra<Item>("escolhido")!!

        setInformationViews()
        setCliqueListeners()
    }
    private fun setInformationViews(){
        binding.tvName.text = prodSelec.name
        binding.tvDescricao.text = prodSelec.description
        binding.tvPrice.text = "R$ %.2f".format(prodSelec.price)
        binding.tvLoja.text = prodSelec.sellerID.toString()
//        binding.ratingBar.rating = prodSelec.aveAvaliacao.toFloat()
        Glide.with(this@DetailActivity)
            .load(prodSelec.image)
            .into(binding.ivThumb)
    }
    private fun setCliqueListeners(){
        binding.ivMenos.setOnClickListener(){
            var qtd = binding.tvQtd.text.toString().toInt()
            if(qtd > 0){
                qtd--
                var total = prodSelec.price * qtd
                binding.tvQtd.text = qtd.toString()
                binding.tvTotal.text = "%.2f".format(total)
            }
        }
        binding.ivMais.setOnClickListener(){
            var qtd = binding.tvQtd.text.toString().toInt()
            if(qtd < prodSelec.stock){
                qtd++
                var total = prodSelec.price * qtd
                binding.tvQtd.text = qtd.toString()
                binding.tvTotal.text = "%.2f".format(total)
            }
        }
        binding.fabBack.setOnClickListener(){
            finish()
        }
        binding.btnAddCarrinho.setOnClickListener() {
            if (binding.tvQtd.text.toString().toInt() != 0){
                if(CartInstance.Carro.storeID != null && CartInstance.Carro.storeID != prodSelec.sellerID.toString()){
                    showDialogBox().thenAccept { result ->
                        if(result){
                            CartInstance.Carro.itens.clear()
                            CartInstance.Carro.storeID = null
                            CartInstance.Carro.shippingPrice = null
                            CartInstance.addItem(
                                this,
                                prodSelec.sellerID.toString(),
                                prodSelec,
                                binding.tvQtd.text.toString().toInt(),
                                0.0
                            )
                            //show in the log all the data of the cart
                            CartInstance.Carro.itens.forEach {
                                Log.d(
                                    "CART",
                                    "Item: ${it.name} - Qtd: ${it.qtd} - Total: ${it.qtd * it.price}"
                                )
                            }
                            finish()
                        }
                    }
                    Log.d("CART1", "Carrinho de outra loja")
                }
                else {
                    CartInstance.addItem(
                        this,
                        prodSelec.sellerID.toString(),
                        prodSelec,
                        binding.tvQtd.text.toString().toInt(),
                        0.0
                    )
                    //show in the log all the data of the cart
                    CartInstance.Carro.itens.forEach {
                        Log.d(
                            "CART",
                            "Item: ${it.name} - Qtd: ${it.qtd} - Total: ${it.qtd * it.price}"
                        )
                    }
                    finish()
                }
            }
        }
    }
    private fun showDialogBox(): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_box)
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
}
