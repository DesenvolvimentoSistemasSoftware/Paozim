package com.mobile.paozim.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mobile.paozim.classes.Product
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var prodSelec: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            prodSelec = intent.getParcelableExtra("escolhido",Product::class.java)!!
        else
            prodSelec = intent.getParcelableExtra<Product>("escolhido")!!

        setInformationViews()
        setCliqueListeners()
    }
    private fun setInformationViews(){
        binding.tvName.text = prodSelec.nome
        binding.tvDescricao.text = prodSelec.descricao
        binding.tvPrice.text = "R$ %.2f".format(prodSelec.preco)
        binding.tvLoja.text = prodSelec.vendedorID.toString()
        binding.ratingBar.rating = prodSelec.aveAvaliacao.toFloat()
        Glide.with(this@DetailActivity)
            .load(prodSelec.imagens[0])
            .into(binding.ivThumb)
    }
    private fun setCliqueListeners(){
        binding.ivMenos.setOnClickListener(){
            var qtd = binding.tvQtd.text.toString().toInt()
            if(qtd > 0){
                qtd--
                var total = prodSelec.preco * qtd
                binding.tvQtd.text = qtd.toString()
                binding.tvTotal.text = "%.2f".format(total)
            }
        }
        binding.ivMais.setOnClickListener(){
            var qtd = binding.tvQtd.text.toString().toInt() + 1
            var total = prodSelec.preco * qtd
            binding.tvQtd.text = qtd.toString()
            binding.tvTotal.text = "%.2f".format(total)
        }
        binding.fabBack.setOnClickListener(){
            finish()
        }
        binding.btnAddCarrinho.setOnClickListener() {
            if (binding.tvQtd.text.toString().toInt() != 0){
                CartInstance.addItem(
                    this,
                    prodSelec.vendedorID,
                    prodSelec,
                    binding.tvQtd.text.toString().toInt(),
                    0.0
                )
                //show in the log all the data of the cart
                CartInstance.Carro.itens.forEach {
                    Log.d("CART", "Item: ${it.name} - Qtd: ${it.qtd} - Total: ${it.qtd * it.price}")
                }
                finish()
            }
        }
    }
}
