package com.mobile.paozim.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobile.paozim.activities.profile.AccountSettingsActivity
import com.mobile.paozim.activities.profile.HistoryActivity
import com.mobile.paozim.classes.userStuff.UserInstance
import com.mobile.paozim.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnAccountSettings.setOnClickListener {
            val context = requireContext()
            val intent = Intent(context, AccountSettingsActivity::class.java)
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            val context = requireContext()
            val intent = Intent(context, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.btnExit.setOnClickListener {
            UserInstance.logout()
            requireActivity().finish()
        }

        val etEmail = binding.etEmail
        val etName = binding.etName

        if (UserInstance.logged) {
            etEmail.text = UserInstance.Usuario.email
            etName.text = UserInstance.Usuario.nome
        } else {
            etEmail.text = "convidado@convidado.com"
            etName.text = "Convidado"
        }

        return binding.root
    }
}
