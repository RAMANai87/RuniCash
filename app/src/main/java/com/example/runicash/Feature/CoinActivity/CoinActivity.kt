package com.example.runicash.Feature.CoinActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.runicash.R
import com.example.runicash.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}