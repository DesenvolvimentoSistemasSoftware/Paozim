package com.mobile.paozim.activities.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.mobile.paozim.databinding.ActivityChangeNameBinding
import com.mobile.paozim.retrofit.RetroInsta
import com.mobile.paozim.retrofit.UserAPI

class ChangeNameActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChangeNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonChangeName.setOnClickListener() {
            changeName(binding.etName.text.toString())
        }

        binding.buttonCancel.setOnClickListener() {
            val i = Intent(this@ChangeNameActivity, AccountSettingsActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun changeName(name: String) {
        val retIn = RetroInsta.getRetrofitInstance().create(UserAPI::class.java)
        // TODO - implementar rota de mudança de nome no backend
    }
}
