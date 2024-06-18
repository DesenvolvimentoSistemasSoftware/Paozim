package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.classes.UserStuff.LoginRequest
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.data.remote.models.UserResponse
import com.mobile.paozim.databinding.ActivityLoginBinding
import com.mobile.paozim.retrofit.UserAPI
import com.mobile.paozim.retrofit.RetroInsta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSignUp.setOnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        }
        binding.fabBack.setOnClickListener {
            finish()
        }
        binding.btnSignIn.setOnClickListener {
            if (checkEmail() && checkPassword()) {
                signIn(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
        binding.btnGuest.setOnClickListener {
            val i = Intent(this@LoginActivity, TabActivity::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        if (UserInstance.logged) {
            val i = Intent(this@LoginActivity, TabActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun signIn(email: String, password: String){
        val retIn = RetroInsta.getRetrofitInstance().create(UserAPI::class.java)
        val loginRequest = LoginRequest(email,password)

        retIn.login(loginRequest).enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val userResponse = response.body()
                if(userResponse?.user != null) {
                    Log.d("LOGIN", "${userResponse.message} e ${userResponse.success}")
                    Toast.makeText(this@LoginActivity, userResponse.message, Toast.LENGTH_SHORT).show()
                    userResponse.user.senha = binding.etPassword.text.toString()
                    UserInstance.setUser(this@LoginActivity, userResponse.user)
                    startActivity(Intent(this@LoginActivity, TabActivity::class.java))
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val regex = """"message":"(.*?)"""".toRegex()
                    val matchResult = regex.find(errorBody.toString())
                    var message = matchResult?.groups?.get(1)?.value
                    if(message == null) message = "Login falhou"
                    Log.d("LOGIN", "Error: $errorBody")
                    Toast.makeText(this@LoginActivity,message, Toast.LENGTH_SHORT).show()
                }
            }
        })
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
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
}
