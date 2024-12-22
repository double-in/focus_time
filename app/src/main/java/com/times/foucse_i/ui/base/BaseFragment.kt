package com.times.foucse_i.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.times.foucse_i.R
import com.times.foucse_i.util.WindowUtil

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarColor()
    }

    override fun onResume() {
        super.onResume()
        updateStatusBarColor()
    }

    protected open fun getStatusBarColor(): Int? = R.color.fragment_background

    protected open fun isLightStatusBar(): Boolean = 
        AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES

    private fun updateStatusBarColor() {
        activity?.window?.let { window ->
            getStatusBarColor()?.let { color ->
                window.statusBarColor = ContextCompat.getColor(requireContext(), color)
            } ?: run {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
            }
            WindowUtil.setLightStatusBar(window, isLightStatusBar())
        }
    }
} 