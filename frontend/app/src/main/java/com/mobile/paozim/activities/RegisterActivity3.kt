package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.data.remote.models.SimpleResponse
import com.mobile.paozim.databinding.ActivityRegister3Binding
import com.mobile.paozim.retrofit.RetroInsta
import com.mobile.paozim.retrofit.UserAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity3 : ComponentActivity() {
    private lateinit var binding: ActivityRegister3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener(){
            finish()
        }

        binding.btnSignUp.setOnClickListener(){
            UserInstance.Usuario.cidade = binding.etCidade.text.toString()
            UserInstance.Usuario.estado = binding.etEstado.text.toString()
            UserInstance.Usuario.endereco = binding.etEndereco.text.toString()
            UserInstance.Usuario.bairro = binding.etBairro.text.toString()
            UserInstance.Usuario.numResidencia = binding.etNumeroResidencia.text.toString().toInt()
            UserInstance.Usuario.complemento = binding.etComplemento.text.toString()
            UserInstance.Usuario.referencia = binding.etPontoReferencia.text.toString()
            signUp()
        }
    }

    private fun signUp(){
        val retIn = RetroInsta.getRetrofitInstance().create(UserAPI::class.java)
        val registerInfo = UserInstance.Usuario

        retIn.register(registerInfo).enqueue(object : Callback<SimpleResponse> {
            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity3,t.message, Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                var simpleResponse = response.body()
                if(simpleResponse != null) {
                    Log.d("CADASTRO", "${simpleResponse.message} e ${simpleResponse.success}")
                    Toast.makeText(this@RegisterActivity3,simpleResponse.message, Toast.LENGTH_SHORT).show()
                    if(simpleResponse.success == "true"){
                        UserInstance.logged = true
                        UserInstance.saveUser(this@RegisterActivity3)
                        startActivity(Intent(this@RegisterActivity3, TabActivity::class.java))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("CADASTRO", "Error: $errorBody")
                    Toast.makeText(this@RegisterActivity3,"O cadastro falhou", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    // fazer funções de análise da correturde do input
}
