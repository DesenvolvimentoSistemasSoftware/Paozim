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

    private fun checkPassword(pass: String): Boolean{
        var error: String? = null
        if(pass.isEmpty()){
            error = "Coloque a senha"
        } else if(pass.length < 6 || pass.length > 15){
            error = "Senha inválida"
        }
        if(error != null){
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)

        binding.btnChangeName.setOnClickListener() {
            val i = Intent(this, ChangeNameActivity::class.java)
            startActivity(i)
        }

        binding.btnBack.setOnClickListener() {
            finish()
        }

        binding.btnLogout.setOnClickListener() {
            // Limpar o UserInstance e voltar para a tela de login
            UserInstance.clearUser(this)
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnDeleteAccount.setOnClickListener() {
            // Fazer a requisição para deletar a conta
            // TODO - pegar senha do usuário
            val password = binding.etPassword.text.toString()
            if (checkPassword(password)) {
                Toast.makeText(this, "Digite a senha para deletar a conta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val userToDelete = DeleteRequest(UserInstance.Usuario.email, "senha")
            retIn.deleteUser(userToDelete).enqueue(object : retrofit2.Callback<Void> {
                override fun onFailure(call: retrofit2.Call<Void>, t: Throwable) {
                    Toast.makeText(this@AccountSettingsActivity, "Erro ao deletar a conta", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: retrofit2.Call<Void>, response: retrofit2.Response<Void>) {
                    // Se der certo, limpar o UserInstance e voltar para a tela de login
                    UserInstance.clearUser(this@AccountSettingsActivity)
                    val i = Intent(this@AccountSettingsActivity, LoginActivity::class.java)
                    startActivity(i)
                }
            })
        }

        val etEmail = binding.etEmail
        val etName = binding.etName

        etEmail.text = UserInstance.Usuario.email
        etName.text = UserInstance.Usuario.nome
    }
}
