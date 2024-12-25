package com.times.foucse_i.ui.base

import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.let { window ->
            WindowCompat.getInsetsController(window, window.decorView).apply {
                isAppearanceLightStatusBars = isLightStatusBar()
                isAppearanceLightNavigationBars = isLightStatusBar()
            }
        }
    }

    protected open fun isLightStatusBar(): Boolean = true
} 