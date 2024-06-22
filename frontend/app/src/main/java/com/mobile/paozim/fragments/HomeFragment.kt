package com.mobile.paozim.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mobile.paozim.R
import com.mobile.paozim.activities.DetailActivity
import com.mobile.paozim.classes.Item
import com.mobile.paozim.databinding.FragmentHomeBinding
import com.mobile.paozim.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm: HomeViewModel
    private lateinit var randomItem: Item

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
        homeMvvm.getRandomItem()
        observeRandomItem()
        onRandomItemClick()
        onSearchIconClick()
    }

    private fun onRandomItemClick() {
        binding.cvComidaRandom.setOnClickListener {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("escolhido", randomItem)
            startActivity(intent)
        }
    }
    private fun observeRandomItem() {
        homeMvvm.observeRandomItemLiveData().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.image)
                .into(binding.imgComidaRandom)
            this.randomItem = value
        }
    }
    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }
}
