package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mobile.paozim.classes.UserStuff.UserInstance
import com.mobile.paozim.databinding.ActivityRegister2Binding

class RegisterActivity2 : ComponentActivity() {
    private lateinit var binding: ActivityRegister2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabBack.setOnClickListener(){
            finish()
        }

        binding.btnNext.setOnClickListener(){
            UserInstance.Usuario.CPF = binding.etCpf.text.toString()
            UserInstance.Usuario.telefone = binding.etTelefone.text.toString()
            UserInstance.Usuario.CEP = binding.etCep.text.toString()
            startActivity(Intent(this, RegisterActivity3::class.java))
        }
    }
    // fazer funções de análise da correturde do input
}
