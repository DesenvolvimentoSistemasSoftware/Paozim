package com.mobile.paozim.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.data.remote.models.SimpleResponse
import com.mobile.paozim.data.remote.models.UserF
import com.mobile.paozim.databinding.ActivityLoginBinding
import com.mobile.paozim.retrofit.UserAPI
import com.mobile.paozim.utils.RetroInsta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"
    var PWD_KEY = "pwd"
    var email = ""
    var pwd = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()
        pwd = sharedPreferences.getString(PWD_KEY, "").toString()

        binding.tvSignUp.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }
        binding.fabBack.setOnClickListener {
            finish()
        }
        binding.btnSignIn.setOnClickListener {
            if (validadeEmail() && validadePassword()) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(EMAIL_KEY, binding.etEmail.text.toString())
                editor.putString(PWD_KEY, binding.etPassword.text.toString())
                editor.apply()

                signin(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (email.isNotEmpty() && pwd.isNotEmpty()) {
//            signin(email, pwd)
            val i = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun funfa(simpleResponse: SimpleResponse){
        if (simpleResponse.message != "NAO") {
            Log.d("LOGIN", "Chegou")
            val i = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(i)
            finish()
        } else {
            Log.d("LOGIN", "OHMYGOD!!!")
        }
    }

    private fun signin(email: String, password: String){
        val retIn = RetroInsta.getRetrofitInstance().create(UserAPI::class.java)
        val loginInfo = UserF("",email,password)
        retIn.login(loginInfo).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                var simpleResponse = response.body()
                if(simpleResponse != null) {
                    Log.d("LOGIN", "${simpleResponse.message} e ${simpleResponse.sucess}")
                    Toast.makeText(this@LoginActivity,"Login success!", Toast.LENGTH_SHORT).show()
                    funfa(simpleResponse)
                    //AuthToken.getInstance(application.baseContext).token = simpleResponse.message
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("LOGIN", "Error: $errorBody")
                    Toast.makeText(this@LoginActivity,"Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
        })
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
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
}
