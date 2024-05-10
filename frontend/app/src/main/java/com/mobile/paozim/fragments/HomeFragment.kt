package com.mobile.paozim.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mobile.paozim.activities.DetailActivity
import com.mobile.paozim.classes.Product
import com.mobile.paozim.databinding.FragmentHomeBinding
import com.mobile.paozim.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomProduct: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
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
        homeMvvm.getRandomProduct()
        observeRandomProduct()
        onRandomProductClick()
    }

    private fun onRandomProductClick() {
        binding.cvComidaRandom.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("escolhido", randomProduct)
            startActivity(intent)
        }
    }

    private fun observeRandomProduct() {
        homeMvvm.observeRandomProductLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.imagens[0])
                .into(binding.imgComidaRandom)
            this.randomProduct = value
        }
    }
}
