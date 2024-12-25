package com.times.focus

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.times.focus.databinding.ActivityMainBinding
import com.times.focus.util.NotificationUtil
import com.times.focus.util.WindowUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 权限已授予，创建通知渠道
            NotificationUtil.createNotificationChannel(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupSystemBars()
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // 已有权限，创建通知渠道
                    NotificationUtil.createNotificationChannel(this)
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // 可以在这里显示一个对话框解释为什么需要通知权限
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    // 直接请求权限
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Android 13 以下版本不需要动态请求通知权限
            NotificationUtil.createNotificationChannel(this)
        }
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

    fun hideBottomNavigation() {
        binding.bottomNavView.visibility = View.GONE
        binding.navigationBarSpace.visibility = View.GONE
    }

    fun showBottomNavigation() {
        binding.bottomNavView.visibility = View.VISIBLE
        binding.navigationBarSpace.visibility = View.VISIBLE
    }
}