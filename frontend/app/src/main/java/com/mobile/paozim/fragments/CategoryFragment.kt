package com.mobile.paozim.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.paozim.classes.itemStuff.Item
import com.mobile.paozim.classes.itemStuff.ItemAdapter
import com.mobile.paozim.databinding.FragmentCategoryBinding
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.API.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getString("category")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater,container,false)
        binding.tvTitleCategory.text = category

        binding.ivBackCategory.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.rvCategoryItens.visibility = View.GONE
        binding.tvEmptyCategory.visibility = View.GONE
        binding.pbWhileSearchingCategory.visibility = View.VISIBLE
        setCategoryItems()
        return binding.root
    }

    private fun setCategoryItems(){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ItemAPI::class.java)
        retIn.getItemsByCategory(category).enqueue(object : Callback<List<Item>> {
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                binding.pbWhileSearchingCategory.visibility = View.GONE
                binding.tvEmptyCategory.text = "Não encontramos nenhum\n produto dessa categoria\n : ("
                binding.tvEmptyCategory.visibility = View.VISIBLE
                Log.d("VEJA", "Falhou")
            }
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                val categoryList = response.body()
                binding.pbWhileSearchingCategory.visibility = View.GONE
                if(!categoryList.isNullOrEmpty()){
                    for (item in categoryList){
                        item.image = BASE_URL + item.image
                        Log.d("VEJA", "id: ${item.id} e nome: ${item.name}")
                    }
                    binding.rvCategoryItens.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    binding.rvCategoryItens.adapter = ItemAdapter(categoryList)
                    binding.rvCategoryItens.visibility = View.VISIBLE
                }
                else{
                    binding.tvEmptyCategory.text = "Não encontramos nenhum\n produto dessa categoria\n : ("
                    binding.tvEmptyCategory.visibility = View.VISIBLE
                }
            }
        })
    }
}
