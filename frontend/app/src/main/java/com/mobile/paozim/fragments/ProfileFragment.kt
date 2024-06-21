package com.mobile.paozim.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mobile.paozim.R
import com.mobile.paozim.activities.profile.AccountSettingsActivity
import com.mobile.paozim.activities.profile.HistoryActivity
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Escrever UserInstance.email e UserInstance.nome nos campos visíveis (etEmail e etName)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val etEmail = view.findViewById<TextView>(R.id.et_email)
        val etName = view.findViewById<TextView>(R.id.et_name)

        if (UserInstance.logged) {
            etEmail.text = UserInstance.Usuario.email
            etName.text = UserInstance.Usuario.nome
        } else {
            etEmail.text = "convidado@convidado.com"
            etName.text = "Convidado"
        }
    }
}
