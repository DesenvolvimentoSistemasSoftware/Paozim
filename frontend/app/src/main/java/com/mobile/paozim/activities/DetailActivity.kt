package com.mobile.paozim.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mobile.paozim.classes.Product
import com.mobile.paozim.databinding.ActivityDetailBinding
import com.mobile.paozim.retrofit.ApiClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    }
    private fun setInformationViews(){
        binding.tvName.text = prodSelec.nome
        binding.tvDescricao.text = prodSelec.descricao
        binding.tvPrice.text = "R$ ${prodSelec.preco.toString()}"
        binding.tvLoja.text = prodSelec.vendedorID.toString()
        binding.ratingBar.rating = prodSelec.aveAvaliacao.toFloat()
        Glide.with(this@DetailActivity)
            .load(prodSelec.imagens[0])
            .into(binding.ivThumb)
    }
}



//        lifecycleScope.launch {
//            obterProduct(intent.getStringExtra("escolhido").toString())
//            setInformationViews()
//        }
//    private fun obterProduct(id:String){
//        val call = ApiClient.apiService.getProduct(id)
//        call.enqueue(object : Callback<Product> {
//            override fun onResponse(call: Call<Product>, response: Response<Product>) {
//                if (response.isSuccessful) {
//                    prodSelec = response.body()!!
//                    Log.d("veja", "deu bom ID: ${prodSelec.id} e NAME: ${prodSelec.nome}")
//                }
//            }
//            override fun onFailure(call: Call<Product>, t: Throwable) {
//                Log.d("veja", "deu ruimm")
//            }
//        })
//    }
