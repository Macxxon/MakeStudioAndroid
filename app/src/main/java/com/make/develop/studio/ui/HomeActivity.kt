package com.make.develop.studio.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.make.develop.studio.R
import com.make.develop.studio.databinding.ActivityHomeBinding

class HomeActivity :AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        navController
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    //TODO: Add the id of the fragment that you want to hide the bottom navigation view
                }
            }
    }
}