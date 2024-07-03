package com.mobile.paozim.activities.enter

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.mobile.paozim.classes.userStuff.UserInstance
import com.mobile.paozim.databinding.ActivityRegisterBinding

class RegisterActivity : ComponentActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val next = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            Intent().apply { setResult(RESULT_OK, this) }
            finish()
        }
    }

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
                val intent = Intent(this, RegisterActivity2::class.java)
                next.launch(intent)
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
        } else if(value.length < 6){
            error = "Senha menor que 6 dígitos"
        } else if(value.length > 15){
            error = "Senha maior que 15 dígitos"
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
