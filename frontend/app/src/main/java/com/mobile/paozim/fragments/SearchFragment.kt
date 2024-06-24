package com.mobile.paozim.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.paozim.classes.Item
import com.mobile.paozim.classes.ItemAdapter
import com.mobile.paozim.databinding.FragmentSearchBinding
import com.mobile.paozim.retrofit.BASE_URL
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    // true = search an item name and show all the itens with this name
    // false = search a store name and show all the stores with this name
    private var searchType: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        binding.ivBackFromSearch.setOnClickListener {
            it.findNavController().popBackStack()
        }
        binding.edSearchBox.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                binding.tvEmpty.visibility = View.GONE
                binding.tvTitleSearch.visibility = View.VISIBLE
                binding.pbWhileSearching.visibility = View.VISIBLE
                if(searchType){
                    binding.tvTitleSearch.text = "Produtos"
                    searchItem(binding.edSearchBox.text.toString())
                }
                else {
                    binding.tvTitleSearch.text = "Lojas"
//                    val searchedList = searchItem(binding.edSearchBox.text.toString())
//                    binding.pbWhileSearching.visibility = View.GONE

//                    if(searchedList != null){
//                        //TODO implementar o adapter
//                    } else {
//                        binding.tvEmpty.text = "Não encontramos nenhuma\n loja com esse nome\n : ("
//                        binding.tvEmpty.visibility = View.VISIBLE
//                    }
                }
                true
            }
            else {
                false
            }
        }
        //TODO implementar filtro de pesquisa
        return binding.root
    }

    private fun searchItem(name: String){
        binding.rvSearchedItens.visibility = View.GONE
        val retIn = RetrofitInstance.getRetrofitInstance().create(ItemAPI::class.java)
        retIn.getItemsByName(name).enqueue(object : Callback<List<Item>>{
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                binding.pbWhileSearching.visibility = View.GONE
                binding.tvEmpty.text = "Não encontramos nenhum\n produto com esse nome\n : ("
                binding.tvEmpty.visibility = View.VISIBLE
                Log.d("VEJA", "Falhou")
            }
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                val searchedList = response.body()
                binding.pbWhileSearching.visibility = View.GONE
                if(!searchedList.isNullOrEmpty()){
                    for (item in searchedList){
                        item.image = BASE_URL + item.image
                        Log.d("VEJA", "id: ${item.id} e nome: ${item.name}")
                    }
                    binding.rvSearchedItens.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                    binding.rvSearchedItens.adapter = ItemAdapter(searchedList)
                    binding.rvSearchedItens.visibility = View.VISIBLE
                }
                else{
                    binding.tvEmpty.text = "Não encontramos nenhum\n produto com esse nome\n : ("
                    binding.tvEmpty.visibility = View.VISIBLE
                }
            }
        })
    }
}
