package com.mobile.paozim.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.R
import com.mobile.paozim.activities.PaymentActivity
import com.mobile.paozim.classes.CartStuff.CartAdapter
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.classes.Item
import com.mobile.paozim.classes.OrderStuff.MembershipAdapter
import com.mobile.paozim.classes.OrderStuff.MembershipViewModel
import com.mobile.paozim.classes.SignaturedItem
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.FragmentMembershipBinding
import com.mobile.paozim.retrofit.ItemAPI
import com.mobile.paozim.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembershipFragment : Fragment() {
    private lateinit var binding: FragmentMembershipBinding
    private lateinit var membershipViewModel: MembershipViewModel
    private lateinit var adapters: MutableList<MembershipAdapter>
    private val retIn = RetrofitInstance.getRetrofitInstance().create(ItemAPI::class.java)
    
    private val next = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            updateSignedItems()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMembershipBinding.inflate(inflater,container,false)

        adapters = mutableListOf()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        membershipViewModel = ViewModelProvider(requireActivity()).get(MembershipViewModel::class.java)
        membershipViewModel.triggerUpdateInfo.observe(viewLifecycleOwner, Observer {shouldUpdate ->
            if(shouldUpdate){
                updateSignedItems()
                membershipViewModel.triggerUpdateInfo.value = false
            }
        })

        updateSignedItems()
    }

    fun updateInfo(items: List<Item>){
        if (items.isEmpty()){
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }

        val adapter = MembershipAdapter(items)
        adapters.add(adapter)
        binding.rvItens.adapter = adapter
        binding.rvItens.layoutManager = LinearLayoutManager(context)
    }

    fun updateSignedItems() {
        // Obtém os itens com a API
        return retIn.getSignedItems(UserInstance.Usuario.email).enqueue(object :
            Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if(response.body() != null){
                    var signedItems: List<Item> = response.body()!!
                    updateInfo(signedItems)
                } else {
                    return
                }
            }
            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.d("VEJA", "Falhou")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        updateSignedItems()
    }
}
