package com.mobile.paozim

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.paozim.databinding.ActivityLoginBinding
import com.mobile.paozim.testdata.RetrofitClient
import com.mobile.paozim.testdata.UserInfo
import com.mobile.paozim.testdata.UsersAPI

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var email : String
    private lateinit var senha : String
    private var userInfo : UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener {
            finish()
        }
        binding.btnSignIn.setOnClickListener {
            val service = RetrofitClient.createUserService()
//            val call = service.getUserByName("Pedro")
            val call = service.getUserByName()
            call.enqueue(object : Callback<UserInfo> {
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext,"OK",Toast.LENGTH_SHORT).show()
                        userInfo = response.body()!!
                    }
                }
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
                }
            })

//            val service = RetrofitClient.createUserService()
////            val call : Call<UserInfo> = service.get_him()
//            val call : Call<List<UserInfo>> = service.get_him()
////            call.enqueue(object : Callback<UserInfo>{
//            call.enqueue(object : Callback<List<UserInfo>>{
////                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
//                override fun onResponse(call: Call<List<UserInfo>>, response: Response<List<UserInfo>>) {
//                    Toast.makeText(applicationContext,"OK",Toast.LENGTH_SHORT).show()
//                    val informacoes = response.body()
//                }
////                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
//                override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {
//                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
//                }
//            })

            email = binding.etEmail.text.toString()
            if(userInfo != null){
                senha = userInfo!!.nome
            }
            else {
                senha = binding.etPassword.text.toString()
            }
            val i = Intent(this, BackendActivity::class.java)
            i.putExtra("email", email)
            i.putExtra("senha", senha)
            startActivity(i)
        }
    }
}
