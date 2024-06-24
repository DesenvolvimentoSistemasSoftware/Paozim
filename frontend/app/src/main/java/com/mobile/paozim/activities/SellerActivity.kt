package com.mobile.paozim.activities

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.mobile.paozim.R
import com.mobile.paozim.classes.Item
import com.mobile.paozim.classes.ItemAdapter
import com.mobile.paozim.classes.Seller
import com.mobile.paozim.databinding.ActivitySellerBinding
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SellerActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySellerBinding
    private lateinit var seller: Seller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            seller = intent.getParcelableExtra("vendedor", Seller::class.java)!!
        else
            seller = intent.getParcelableExtra<Seller>("vendedor")!!
        val id = intent.getIntExtra("id", 0)

        binding.fabBackSeller.setOnClickListener {
            finish()
        }

        setInformationViews()
        getItems(id)
    }

    private fun setInformationViews() {
        binding.tvNameSeller.text = seller.nome
        binding.tvDescriptionSeller.text = seller.description
        binding.tvPhoneSeller.text = seller.telefone
        binding.tvCepSeller.text = seller.CEP
        binding.tvCidadeSeller.text = seller.cidade
        binding.tvEstadoSeller.text = seller.estado
        binding.tvNameBairroSeller.text = seller.bairro
        binding.tvNumSeller.text = seller.numResidencia.toString()

        Glide.with(this@SellerActivity)
            .load(seller.image)
            .into(binding.ivThumbSeller)
    }
    private fun getItems(id: Int) {
        binding.rvSellerItems.visibility = View.GONE
        val retIn = RetrofitInstance.getRetrofitInstance().create(ItemAPI::class.java)
        retIn.getItemsBySeller(id).enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                val items = response.body()
                if (!items.isNullOrEmpty()) {
                    for (item in items){
                        item.image = BASE_URL + item.image
                        Log.d("VEJA", "id: ${item.id} e nome: ${item.name}")
                    }
                    binding.pbWhileSearchingSellerItems.visibility = View.GONE
                    binding.rvSellerItems.adapter = ItemAdapter(items)
                    binding.rvSellerItems.layoutManager = GridLayoutManager(this@SellerActivity, 2, GridLayoutManager.VERTICAL, false)
                    binding.rvSellerItems.visibility = View.VISIBLE
                }
            }
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                binding.pbWhileSearchingSellerItems.visibility = View.GONE
                Log.d("SellerActivity", "Error: ${t.message}")
            }
        })
    }
}
