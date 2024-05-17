package com.mobile.paozim.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.paozim.classes.CartStuff.CartAdapter
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(CartInstance.Carro.storeID == null){
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvItens.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvItens.visibility = View.VISIBLE

            binding.tvSubtotal.text = "%.2f".format(CartInstance.getSubtotal())
            binding.tvFrete.text = "%.2f".format(CartInstance.Carro.shippingPrice)
            binding.tvTotalCart.text = "%.2f".format(CartInstance.getSubtotal() + CartInstance.Carro.shippingPrice!!)

            binding.rvItens.adapter = CartAdapter(CartInstance.Carro.itens)
            binding.rvItens.layoutManager = LinearLayoutManager(context)
        }
    }
}
