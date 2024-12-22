package com.times.foucse_i.ui.settings

import androidx.lifecycle.ViewModel
import com.times.foucse_i.data.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    fun isSoundEnabled(): Boolean {
        return preferencesRepository.isSoundEnabled()
    }

    fun setSoundEnabled(enabled: Boolean) {
        preferencesRepository.setSoundEnabled(enabled)
    }

    fun getVolume(): Float {
        return preferencesRepository.getVolume()
    }

    fun setVolume(volume: Float) {
        preferencesRepository.setVolume(volume)
    }

    fun getCurrentTheme(): Int {
        return preferencesRepository.getCurrentTheme()
    }

    fun setTheme(theme: Int) {
        preferencesRepository.setTheme(theme)
    }
} 