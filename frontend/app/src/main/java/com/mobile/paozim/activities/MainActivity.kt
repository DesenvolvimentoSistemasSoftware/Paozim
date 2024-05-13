package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.R
import com.mobile.paozim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the TextView by its ID
        val buttonSignup = findViewById<TextView>(R.id.button_signup)
        val buttonSignin = findViewById<TextView>(R.id.button_signin)

        // Set OnClickListener on the TextView
        buttonSignup.setOnClickListener {
            // Inside OnClickListener, start the new activity using an Intent
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonSignin.setOnClickListener {
            // Inside OnClickListener, start the new activity using an Intent
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
