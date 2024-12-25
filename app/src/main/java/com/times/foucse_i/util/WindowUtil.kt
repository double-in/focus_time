package com.times.foucse_i.util

import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object WindowUtil {
    fun setupImmersiveMode(window: Window) {
        // 启用边到边布局
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    fun enterFocusMode(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            // 隐藏状态栏和导航栏
            hide(WindowInsetsCompat.Type.systemBars())
            // 设置导航栏行为
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun exitFocusMode(window: Window) {
        WindowCompat.getInsetsController(window, window.decorView).apply {
            // 显示状态栏和导航栏
            show(WindowInsetsCompat.Type.systemBars())
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