package com.mobile.paozim.activities.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.R
import com.mobile.paozim.activities.LoginActivity
import com.mobile.paozim.activities.TabActivity
import com.mobile.paozim.classes.UserStuff.DeleteRequest
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityAccountSettingsBinding
import com.mobile.paozim.retrofit.RetrofitInstance
import com.mobile.paozim.retrofit.UserAPI

class AccountSettingsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAccountSettingsBinding
    val retIn = RetrofitInstance.getRetrofitInstance().create(UserAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangeName.setOnClickListener() {
            val i = Intent(this, ChangeNameActivity::class.java)
            startActivity(i)
        }

        binding.btnBack.setOnClickListener() {
            finish()
        }

        binding.btnLogout.setOnClickListener() {
            // Limpar o UserInstance e voltar para a tela de login
            UserInstance.logout()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        val etEmail = binding.etEmail
        val etName = binding.etName

        etEmail.text = UserInstance.Usuario.email
        etName.text = UserInstance.Usuario.nome
    }
}
