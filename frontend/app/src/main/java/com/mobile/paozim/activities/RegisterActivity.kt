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
import com.mobile.paozim.data.remote.models.UserF
import com.mobile.paozim.databinding.ActivityRegisterBinding
import com.mobile.paozim.retrofit.UserAPI
import com.mobile.paozim.retrofit.RetroInsta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : ComponentActivity() {
   private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        binding.fabBack.setOnClickListener(){
            finish()
        }

        binding.btnSignUp.setOnClickListener(){
            if(validateName() && validadeEmail() && validadePassword() && validadePasswordAndConfirm()){
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(EMAIL_KEY, binding.etEmail.text.toString())
                editor.putString(PWD_KEY, binding.etPassword.text.toString())
                editor.apply()

                signup(
                    binding.etEmail.text.toString(),
                    binding.etNome.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
    }

    private fun funfa(simpleResponse: SimpleResponse){
        if (simpleResponse.message != "Some error ocurred" && simpleResponse.message != "Missing some fields") {
            Log.d("CADASTRO", "Chegou")
            val i = Intent(this@RegisterActivity, TabActivity::class.java)
            startActivity(i)
        } else {
            Log.d("CADASTRO", "OHMYGOD!!!")
        }
    }

    private fun signup(email: String, name: String,password: String){
        val retIn = RetroInsta.getRetrofitInstance().create(UserAPI::class.java)
        val registerInfo = UserF(name,email,password)

        retIn.register(registerInfo).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity,t.message,Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                var simpleResponse = response.body()
                if(simpleResponse != null) {
                    Log.d("CADASTRO", "${simpleResponse.message} e ${simpleResponse.sucess}")
                    Toast.makeText(this@RegisterActivity,"Registration success!", Toast.LENGTH_SHORT).show()
                    funfa(simpleResponse)
//                    AuthToken.getInstance(application.baseContext).token = simpleResponse.message
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("CADASTRO", "Error: $errorBody")
                    Toast.makeText(this@RegisterActivity,"Registration failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateName(): Boolean{
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
    private fun validadeEmail(): Boolean{
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
    private fun validadePassword(): Boolean{
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
    private fun validadePasswordAndConfirm(): Boolean{
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
