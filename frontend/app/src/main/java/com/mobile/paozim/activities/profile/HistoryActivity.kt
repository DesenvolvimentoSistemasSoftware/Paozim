package com.mobile.paozim.activities.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mobile.paozim.databinding.ActivityHistoryBinding

class HistoryActivity : ComponentActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
    }
}

