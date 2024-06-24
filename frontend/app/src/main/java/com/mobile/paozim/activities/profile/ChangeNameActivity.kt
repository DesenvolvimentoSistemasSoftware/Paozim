package com.mobile.paozim.activities.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.classes.UserStuff.ChangeNameRequest

import com.mobile.paozim.databinding.ActivityChangeNameBinding
import com.mobile.paozim.retrofit.RetrofitInstance
import com.mobile.paozim.retrofit.UserAPI

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.widget.Toast
import com.mobile.paozim.classes.Responses.SimpleResponse
import com.mobile.paozim.classes.UserStuff.UserInstance


class ChangeNameActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChangeNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonChangeName.setOnClickListener() {
            changeName(binding.etName.text.toString(), binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.buttonCancel.setOnClickListener() {
            finish()
        }
    }

    private fun changeName(newName: String, email: String, password: String) {
        val retIn = RetrofitInstance.getRetrofitInstance().create(UserAPI::class.java)
        val changeNameRequest = ChangeNameRequest(email, password, newName)
        
        retIn.changeName(changeNameRequest).enqueue(object: Callback<SimpleResponse> {
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                if (response.isSuccessful) {
                    UserInstance.Usuario.nome = newName
                    val i = Intent(this@ChangeNameActivity, AccountSettingsActivity::class.java)
                    startActivity(i)
                    finish()
                } else {
                    Toast.makeText(this@ChangeNameActivity, "Error changing name", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Toast.makeText(this@ChangeNameActivity, "Error changing name", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
