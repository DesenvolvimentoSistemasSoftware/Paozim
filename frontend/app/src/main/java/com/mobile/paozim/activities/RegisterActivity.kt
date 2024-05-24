package com.mobile.paozim.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.mobile.paozim.data.remote.models.SimpleResponse
import com.mobile.paozim.classes.UserStuff.User
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityRegisterBinding
import com.mobile.paozim.retrofit.UserAPI
import com.mobile.paozim.retrofit.RetroInsta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener(){
            finish()
        }

        binding.btnSignUp.setOnClickListener(){
            if(checkName() && checkEmail() && checkPassword() && checkPasswordAndConfirm()){
                UserInstance.Usuario.nome = binding.etNome.text.toString()
                UserInstance.Usuario.email = binding.etEmail.text.toString()
                UserInstance.Usuario.senha = binding.etPassword.text.toString()
                startActivity(Intent(this, RegisterActivity2::class.java))
            }
        }
    }

    private fun checkName(): Boolean{
        var error: String? = null
        val value: String = binding.etNome.text.toString()
        if(value.isEmpty()){
            error = "Coloque o nome"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkEmail(): Boolean{
        var error: String? = null
        val value: String = binding.etEmail.text.toString()
        if(value.isEmpty()){
            error = "Coloque o email"
        } else if(!Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            error = "Email inválido"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkPassword(): Boolean{
        var error: String? = null
        val value: String = binding.etPassword.text.toString()
        if(value.isEmpty()){
            error = "Coloque a senha"
        } else if(value.length < 6 || value.length > 15){
            error = "Senha inválida"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkPasswordAndConfirm(): Boolean{
        var error: String? = null
        val value: String = binding.etPassword.text.toString()
        val value2: String = binding.etConfirmPassword.text.toString()
        if(value != value2){
            error = "As senhas não são iguais"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
}
