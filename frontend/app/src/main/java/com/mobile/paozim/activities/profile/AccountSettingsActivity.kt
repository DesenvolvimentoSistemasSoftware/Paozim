package com.mobile.paozim.activities.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.R
import com.mobile.paozim.activities.LoginActivity
import com.mobile.paozim.activities.TabActivity
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityAccountSettingsBinding

class AccountSettingsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAccountSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)

        binding.btnChangeName.setOnClickListener() {
            val i = Intent(this, ChangeNameActivity::class.java)
            startActivity(i)
        }

        binding.btnBack.setOnClickListener() {
            val i = Intent(this, TabActivity::class.java)
            startActivity(i)
        }

        binding.btnLogout.setOnClickListener() {
            // Limpar o UserInstance e voltar para a tela de login
            UserInstance.clearUser(this)
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnDeleteAccount.setOnClickListener() {
            // TODO - apagar usuário do banco de dados
            UserInstance.clearUser(this)
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
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
    }
}
