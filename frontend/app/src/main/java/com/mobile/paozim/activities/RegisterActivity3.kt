package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.text.isDigitsOnly
import com.mobile.paozim.classes.Adress
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.classes.Responses.SimpleResponse
import com.mobile.paozim.databinding.ActivityRegister3Binding
import com.mobile.paozim.retrofit.AdressAPI
import com.mobile.paozim.retrofit.RetrofitInstance
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

        setFieldsAdress()

        binding.fabBack.setOnClickListener(){
            finish()
        }

        binding.btnSignUp.setOnClickListener(){
            if(checkCidade() && checkEstado() && checkEndereco() && checkBairro() && checkNumeroResidencia()) {
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
    }

    private fun checkCidade(): Boolean{
        var error: String? = null
        val value: String = binding.etCidade.text.toString()
        if(value.isEmpty()){
            error = "Coloque a cidade"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkEstado(): Boolean{
        var error: String? = null
        val value: String = binding.etEstado.text.toString()
        if(value.isEmpty()){
            error = "Coloque o estado"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkEndereco(): Boolean{
        var error: String? = null
        val value: String = binding.etEndereco.text.toString()
        if(value.isEmpty()){
            error = "Coloque o endereço"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkBairro(): Boolean{
        var error: String? = null
        val value: String = binding.etBairro.text.toString()
        if(value.isEmpty()){
            error = "Coloque o bairro"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkNumeroResidencia(): Boolean{
        var error: String? = null
        val value: String = binding.etNumeroResidencia.text.toString()
        if(value.isEmpty()){
            error = "Coloque o número da residência"
        } else if(!value.isDigitsOnly()){
            error = "O número da residência deve ser um número"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }

    private fun setFieldsAdress(){
        val cep = UserInstance.Usuario.CEP
        val retIn = RetrofitInstance.getRetrofitInstance().create(AdressAPI::class.java)

        retIn.getAdress(cep).enqueue(object : Callback<Adress> {
            override fun onFailure(call: Call<Adress>, t: Throwable) {
                Log.d("CADASTRO", t.message.toString())
            }
            override fun onResponse(call: Call<Adress>, response: Response<Adress>) {
                var adress = response.body()
                if(adress != null) {
                    binding.etCidade.setText(adress.localidade)
                    binding.etEstado.setText(adress.uf)
                    binding.etEndereco.setText(adress.logradouro)
                    binding.etBairro.setText(adress.bairro)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("CADASTRO", "Error: $errorBody")
                }
            }
        })
    }

    private fun signUp(){
        val retIn = RetrofitInstance.getRetrofitInstance().create(UserAPI::class.java)
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
                        Intent().apply { setResult(RESULT_OK, this) }
                        startActivity(Intent(this@RegisterActivity3, TabActivity::class.java))
                        finish()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("CADASTRO", "Error: $errorBody")
                    Toast.makeText(this@RegisterActivity3,"O cadastro falhou", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
