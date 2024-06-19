package com.mobile.paozim.activities.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.classes.UserStuff.ChangeNameRequest

import com.mobile.paozim.databinding.ActivityChangeNameBinding
import com.mobile.paozim.retrofit.RetroInsta
import com.mobile.paozim.retrofit.UserAPI

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.widget.Toast
import com.mobile.paozim.data.remote.models.SimpleResponse


class ChangeNameActivity: AppCompatActivity() {
    private lateinit var binding: ActivityChangeNameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonChangeName.setOnClickListener() {
            changeName(binding.etName.text.toString(), binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.buttonCancel.setOnClickListener() {
            val i = Intent(this@ChangeNameActivity, AccountSettingsActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun changeName(newName: String, email: String, password: String) {
        val retIn = RetroInsta.getRetrofitInstance().create(UserAPI::class.java)
        val changeNameRequest = ChangeNameRequest(email, password, newName)
        
        retIn.changeName(changeNameRequest).enqueue(object: Callback<SimpleResponse> {
            override fun onResponse(call: Call<SimpleResponse>, response: Response<SimpleResponse>) {
                if (response.isSuccessful) {
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