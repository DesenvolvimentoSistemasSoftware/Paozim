package com.mobile.paozim.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobile.paozim.R
import com.mobile.paozim.classes.CartStuff.CartInstance
import com.mobile.paozim.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CartInstance.loadCart(this)

        // Find the TextView by its ID
        val buttonNext = findViewById<TextView>(R.id.btn_next)

        buttonNext.setOnClickListener {
            // Inside OnClickListener, start the new activity using an Intent
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
