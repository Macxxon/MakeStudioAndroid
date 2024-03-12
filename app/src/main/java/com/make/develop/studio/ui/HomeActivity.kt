package com.make.develop.studio.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.make.develop.studio.databinding.ActivityHomeBinding

class HomeActivity :AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.myTitleTxt.text = "KEVIN ES UN PRO"

        binding.loginBtn.setOnClickListener {
            finish()
        }
    }
}