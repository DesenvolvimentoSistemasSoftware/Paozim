package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.databinding.ActivityLoginBinding
import com.mobile.paozim.testdata.RetrofitClient
import com.mobile.paozim.testdata.UserInfo

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var id by Delegates.notNull<Int>()
    private lateinit var email : String
    private lateinit var senha : String
//    private var userInfo : UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener {
            finish()
        }
        binding.btnSignIn.setOnClickListener {

            var firstId: String? = null
            val service = RetrofitClient.createUserService()
            val call : Call<List<UserInfo>> = service.getUserByName()

            call.enqueue(object : Callback<List<UserInfo>>{
                override fun onResponse(call: Call<List<UserInfo>>, response: Response<List<UserInfo>>) {
                    Toast.makeText(applicationContext,"OK",Toast.LENGTH_SHORT).show()
                    val informacoes = response.body()
                    firstId = informacoes?.getOrNull(0)?.telefone
                    Log.d("veja", "aqui oh: $firstId")
                }
                override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
                }
            })

            email = binding.etEmail.text.toString()
            senha = binding.etPassword.text.toString()
            val i = Intent(this, BackendActivity::class.java)
            Log.d("veja", "aqui oh2: $firstId")
            i.putExtra("firstId", firstId)
            i.putExtra("email", email)
            i.putExtra("senha", senha)
            startActivity(i)
        }
    }
}
