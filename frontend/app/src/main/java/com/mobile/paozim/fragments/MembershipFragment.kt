package com.mobile.paozim.fragments

import com.mobile.paozim.classes.signatureStuff.Signature
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.paozim.classes.signatureStuff.SignatureAdapter
import com.mobile.paozim.classes.userStuff.UserInstance
import com.mobile.paozim.databinding.FragmentMembershipBinding
import com.mobile.paozim.retrofit.RetrofitInstance
import com.mobile.paozim.retrofit.API.SignatureAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembershipFragment : Fragment() {
    private lateinit var binding: FragmentMembershipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMembershipBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvEmptySignatures.visibility = View.GONE
        binding.pbMembership.visibility = View.VISIBLE
        binding.rvItensSignatures.visibility = View.GONE
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val retIn = RetrofitInstance.getRetrofitInstance().create(SignatureAPI::class.java)
        retIn.getSignedItems(UserInstance.Usuario.email).enqueue(object : Callback<List<Signature>> {
            override fun onFailure(call: Call<List<Signature>>, t: Throwable) {
                binding.tvEmptySignatures.visibility = View.VISIBLE
                binding.pbMembership.visibility = View.GONE
                Log.d("VEJA", "Falhou")
            }
            override fun onResponse(call: Call<List<Signature>>, response: Response<List<Signature>>) {
                val signatureList = response.body()
                binding.pbMembership.visibility = View.GONE
                if(!signatureList.isNullOrEmpty()){
                    binding.rvItensSignatures.layoutManager = LinearLayoutManager(context)
                    binding.rvItensSignatures.adapter = SignatureAdapter(signatureList)
                    binding.rvItensSignatures.visibility = View.VISIBLE
                }
                else{
                    binding.tvEmptySignatures.visibility = View.VISIBLE
                }
            }
        })
    }
}
