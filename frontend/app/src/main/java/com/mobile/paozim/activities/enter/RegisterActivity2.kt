package com.mobile.paozim.activities.enter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.isDigitsOnly
import com.mobile.paozim.classes.userStuff.UserInstance
import com.mobile.paozim.databinding.ActivityRegister2Binding

class RegisterActivity2 : ComponentActivity() {
    private lateinit var binding: ActivityRegister2Binding
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
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener(){
            finish()
        }

        binding.btnNext.setOnClickListener(){
            if(checkCPF() && checkTelefone() && checkCEP()) {
                UserInstance.Usuario.CPF = binding.etCpf.text.toString()
                UserInstance.Usuario.telefone = binding.etTelefone.text.toString()
                UserInstance.Usuario.CEP = binding.etCep.text.toString()
                val intent = Intent(this, RegisterActivity3::class.java)
                next.launch(intent)
            }
        }
    }

    private fun checkCPF(): Boolean{
        var error: String? = null
        val value: String = binding.etCpf.text.toString()
        if(value.isEmpty()){
            error = "Coloque o CPF"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkTelefone(): Boolean{
        var error: String? = null
        val value: String = binding.etTelefone.text.toString()
        if(value.isEmpty()){
            error = "Coloque o telefone"
        } else if (!value.isDigitsOnly()){
            error = "Telefone inválido"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
    private fun checkCEP(): Boolean{
        var error: String? = null
        val value: String = binding.etCep.text.toString()
        if(value.isEmpty()){
            error = "Coloque o CEP"
        } else if (!value.isDigitsOnly() || value.length != 8){
            error = "CEP inválido"
        }
        if(error != null){
            Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
        }
        return error == null
    }
}
