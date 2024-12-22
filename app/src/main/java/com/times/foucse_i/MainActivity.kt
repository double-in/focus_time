package com.times.foucse_i

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.times.foucse_i.databinding.ActivityMainBinding
import com.times.foucse_i.util.WindowUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupSystemBars()
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
    }

    private fun setupSystemBars() {
        WindowUtil.setupImmersiveMode(window)
        
        WindowUtil.applySystemBarInsets(binding.root) { statusBarHeight, navigationBarHeight ->
            binding.statusBarSpace.layoutParams.height = statusBarHeight
            binding.statusBarSpace.requestLayout()
            binding.navigationBarSpace.layoutParams.height = navigationBarHeight
            binding.navigationBarSpace.requestLayout()
        }
    }
}