package com.mobile.paozim.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.paozim.activities.PaymentActivity
import com.mobile.paozim.classes.cartStuff.CartAdapter
import com.mobile.paozim.classes.cartStuff.CartInstance
import com.mobile.paozim.viewModel.CartViewModel
import com.mobile.paozim.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private val next = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            updateInfo()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        binding.btnPedido.setOnClickListener(){
            val intent = Intent(activity, PaymentActivity::class.java)
            next.launch(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)
        cartViewModel.triggerUpdateInfo.observe(viewLifecycleOwner, Observer {shouldUpdate ->
            if(shouldUpdate){
                updateInfo()
                cartViewModel.triggerUpdateInfo.value = false
            }
        })
        updateInfo()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = CartInstance.Carro.itens[position]
                CartInstance.removeItem(requireContext(), item)
                cartViewModel.triggerUpdateInfo.value = true
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvItens)
    }

    fun updateInfo(){
        if(CartInstance.Carro.storeID == null){
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvItens.visibility = View.GONE
            binding.ll1.visibility = View.GONE
            binding.cl1.visibility = View.GONE
            binding.btnPedido.visibility = View.GONE
            binding.tvResumo.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvItens.visibility = View.VISIBLE
            binding.ll1.visibility = View.VISIBLE
            binding.cl1.visibility = View.VISIBLE
            binding.btnPedido.visibility = View.VISIBLE
            binding.tvResumo.visibility = View.VISIBLE

            binding.tvSubtotal.text = "%.2f".format(CartInstance.getSubtotal())
            binding.tvFrete.text = "%.2f".format(CartInstance.Carro.shippingPrice)
            binding.tvTotalCart.text = "%.2f".format(CartInstance.getSubtotal() + CartInstance.Carro.shippingPrice!!)

            binding.rvItens.adapter = CartAdapter(CartInstance.Carro.itens, cartViewModel)
            binding.rvItens.layoutManager = LinearLayoutManager(context)
        }
    }
}
