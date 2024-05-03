package com.mobile.paozim.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mobile.paozim.R
import com.mobile.paozim.classes.Product
import com.mobile.paozim.databinding.ActivityLoginBinding
import com.mobile.paozim.databinding.FragmentHomeBinding
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RetrofitInstance.api.getRandomProduct().enqueue(object : Callback<Product>{
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if(response.body() != null){
                    val randomProduct: Product = response.body()!!
                    Glide.with(this@HomeFragment)
                        .load(randomProduct.imagens[0])
                        .into(binding.imgComidaRandom)
                    Log.d("VEJA", "id: ${randomProduct.id} e nome: ${randomProduct.nome}")
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(context, "Falhou", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
