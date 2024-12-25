package com.times.focus.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.times.focus.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : Fragment() {
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
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                // Timer settings
                binding.focusDurationSlider.value = state.focusDuration.toFloat()
                binding.customTimeEditText.setText(state.focusDuration.toString())
                binding.shortBreakDurationSlider.value = state.shortBreakDuration.toFloat()
                binding.longBreakDurationSlider.value = state.longBreakDuration.toFloat()
                binding.sessionsSlider.value = state.sessionsBeforeLongBreak.toFloat()

                // Notification settings
                binding.notificationsSwitch.isChecked = state.notificationsEnabled
                binding.vibrationSwitch.isChecked = state.vibrationEnabled
                binding.soundSwitch.isChecked = state.soundEnabled
                binding.volumeSlider.value = state.volume

                // Theme settings
                when (state.theme) {
                    AppCompatDelegate.MODE_NIGHT_NO -> binding.themeLightRadioButton.isChecked = true
                    AppCompatDelegate.MODE_NIGHT_YES -> binding.themeDarkRadioButton.isChecked = true
                    else -> binding.themeSystemRadioButton.isChecked = true
                }

                // Auto-start break
                binding.autoStartBreakSwitch.isChecked = state.autoStartBreak
            }
        }
    }

    private fun setupListeners() {
        // Timer settings
        binding.focusDurationSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                viewModel.setFocusDuration(value.toInt())
                binding.customTimeEditText.setText(value.toInt().toString())
            }
        }

        binding.customTimeEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val customTime = binding.customTimeEditText.text.toString().toIntOrNull()
                if (customTime != null && customTime in 1..120) {
                    viewModel.setFocusDuration(customTime)
                    binding.focusDurationSlider.value = customTime.toFloat()
                } else {
                    val currentValue = binding.focusDurationSlider.value.toInt()
                    binding.customTimeEditText.setText(currentValue.toString())
                }
            }
        }

        binding.shortBreakDurationSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.setShortBreakDuration(value.toInt())
        }
        binding.longBreakDurationSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.setLongBreakDuration(value.toInt())
        }
        binding.sessionsSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.setSessionsBeforeLongBreak(value.toInt())
        }

        // Notification settings
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationsEnabled(isChecked)
        }
        binding.vibrationSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setVibrationEnabled(isChecked)
        }
        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setSoundEnabled(isChecked)
        }
        binding.volumeSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) viewModel.setVolume(value)
        }

        // Theme settings
        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                binding.themeLightRadioButton.id -> AppCompatDelegate.MODE_NIGHT_NO
                binding.themeDarkRadioButton.id -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            viewModel.setTheme(theme)
            AppCompatDelegate.setDefaultNightMode(theme)
        }

        // Auto-start break
        binding.autoStartBreakSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setAutoStartBreak(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 