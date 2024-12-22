package com.times.foucse_i.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.times.foucse_i.R
import com.times.foucse_i.databinding.FragmentSettingsBinding
import com.times.foucse_i.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSoundSettings()
        setupThemeSettings()
    }

    override fun isLightStatusBar(): Boolean = true

    private fun setupSoundSettings() {
        binding.soundEnabledSwitch.apply {
            isChecked = viewModel.isSoundEnabled()
            setOnCheckedChangeListener { _, isChecked ->
                viewModel.setSoundEnabled(isChecked)
            }
        }

        binding.volumeSlider.apply {
            value = viewModel.getVolume()
            addOnChangeListener { _, value, fromUser ->
                if (fromUser) {
                    viewModel.setVolume(value)
                }
            }
        }
    }

    private fun setupThemeSettings() {
        val currentTheme = viewModel.getCurrentTheme()
        when (currentTheme) {
            AppCompatDelegate.MODE_NIGHT_NO -> binding.themeLight.isChecked = true
            AppCompatDelegate.MODE_NIGHT_YES -> binding.themeDark.isChecked = true
            else -> binding.themeSystem.isChecked = true
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.themeLight.id -> {
                    viewModel.setTheme(AppCompatDelegate.MODE_NIGHT_NO)
                }
                binding.themeDark.id -> {
                    viewModel.setTheme(AppCompatDelegate.MODE_NIGHT_YES)
                }
                binding.themeSystem.id -> {
                    viewModel.setTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 