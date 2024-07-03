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
import com.mobile.paozim.classes.categoryStuff.Category
import com.mobile.paozim.classes.categoryStuff.CategoryAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.paozim.classes.itemStuff.Item
import com.mobile.paozim.databinding.FragmentHomeBinding
import com.mobile.paozim.viewModel.HomeViewModel


class HomeFragment : Fragment(), CategoryAdapter.OnItemClickListener {
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMvvm.getRandomItem()
        observeRandomItem()
        onRandomItemClick()
        onSearchIconClick()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val categoryList : List<Category> = listOf(
            Category("Pão", "ic_pao"),
            Category("Bebida", "ic_bebida"),
            Category("Doce", "ic_doce"),
            Category("Frios", "ic_frios"),
            Category("Salgado", "ic_salgado"),
            Category("Bolo", "ic_bolo"),
        )
        binding.rvCategories.adapter = CategoryAdapter(categoryList, this)
        binding.rvCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
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
    override fun onItemClick(category: Category) {
        val bundle = Bundle()
        bundle.putString("category", category.name)
        findNavController().navigate(R.id.action_homeFragment_to_categoryFragment, bundle)
    }
}
