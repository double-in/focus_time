package com.times.foucse_i.util

import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

object WindowUtil {
    fun setupImmersiveMode(window: Window) {
        // 启用边到边布局
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        // 获取 WindowInsetsController
        WindowCompat.getInsetsController(window, window.decorView).apply {
            // 设置系统栏行为
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            // 设置系统栏图标颜色
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }

    fun enterFocusMode(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            // 隐藏状态栏
            hide(WindowInsetsCompat.Type.statusBars())
            // 保持导航栏可见
            show(WindowInsetsCompat.Type.navigationBars())
            // 设置系统栏图标颜色
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }

    fun exitFocusMode(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            // 显示状态栏
            show(WindowInsetsCompat.Type.statusBars())
            // 保持导航栏可见
            show(WindowInsetsCompat.Type.navigationBars())
            // 设置系统栏图标颜色
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }
    }

    fun applySystemBarInsets(view: View, onApplyInsets: (Int, Int) -> Unit) {
        ViewCompat.setOnApplyWindowInsetsListener(view) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            onApplyInsets(insets.top, -insets.bottom)
            windowInsets
        }
    }
} 