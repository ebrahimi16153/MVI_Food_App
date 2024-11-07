package com.github.ebrahimi16153.mvifoodapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.github.ebrahimi16153.mvifoodapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.github.ebrahimi16153.mvifoodapp.R


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //binding
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //nav host
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        // navController
        navController = navHost.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            binding.bottomNav.isVisible = destination.id != R.id.detailFragment
        }


    }
}