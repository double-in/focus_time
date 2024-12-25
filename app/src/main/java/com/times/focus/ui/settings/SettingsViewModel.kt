package com.times.focus.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.times.focus.data.preferences.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            preferencesRepository.focusDuration.combine(
                preferencesRepository.shortBreakDuration
            ) { focus, shortBreak -> Pair(focus, shortBreak) }
                .combine(preferencesRepository.longBreakDuration) { (focus, shortBreak), longBreak ->
                    Triple(focus, shortBreak, longBreak)
                }
                .combine(preferencesRepository.sessionsBeforeLongBreak) { (focus, shortBreak, longBreak), sessions ->
                    PartialSettings(focus, shortBreak, longBreak, sessions)
                }
                .combine(preferencesRepository.notificationsEnabled) { settings, notifications ->
                    settings.copy(notificationsEnabled = notifications)
                }
                .combine(preferencesRepository.vibrationEnabled) { settings, vibration ->
                    settings.copy(vibrationEnabled = vibration)
                }
                .combine(preferencesRepository.soundEnabled) { settings, sound ->
                    settings.copy(soundEnabled = sound)
                }
                .combine(preferencesRepository.volume) { settings, volume ->
                    settings.copy(volume = volume)
                }
                .combine(preferencesRepository.theme) { settings, theme ->
                    settings.copy(theme = theme)
                }
                .combine(preferencesRepository.autoStartBreak) { settings, autoStart ->
                    settings.toUiState(autoStart)
                }
                .collect { state ->
                    _uiState.value = state
                }
        }
    }

    fun setFocusDuration(minutes: Int) {
        viewModelScope.launch {
            preferencesRepository.setFocusDuration(minutes)
        }
    }

    fun setShortBreakDuration(minutes: Int) {
        viewModelScope.launch {
            preferencesRepository.setShortBreakDuration(minutes)
        }
    }

    fun setLongBreakDuration(minutes: Int) {
        viewModelScope.launch {
            preferencesRepository.setLongBreakDuration(minutes)
        }
    }

    fun setSessionsBeforeLongBreak(count: Int) {
        viewModelScope.launch {
            preferencesRepository.setSessionsBeforeLongBreak(count)
        }
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setNotificationsEnabled(enabled)
        }
    }

    fun setVibrationEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setVibrationEnabled(enabled)
        }
    }

    fun setSoundEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setSoundEnabled(enabled)
        }
    }

    fun setVolume(volume: Float) {
        viewModelScope.launch {
            preferencesRepository.setVolume(volume)
        }
    }

    fun setTheme(theme: Int) {
        viewModelScope.launch {
            preferencesRepository.setTheme(theme)
        }
    }

    fun setAutoStartBreak(enabled: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setAutoStartBreak(enabled)
        }
    }
}

private data class PartialSettings(
    val focusDuration: Int,
    val shortBreakDuration: Int,
    val longBreakDuration: Int,
    val sessionsBeforeLongBreak: Int,
    val notificationsEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val volume: Float = 1.0f,
    val theme: Int = 0
) {
    fun toUiState(autoStartBreak: Boolean) = SettingsUiState(
        focusDuration = focusDuration,
        shortBreakDuration = shortBreakDuration,
        longBreakDuration = longBreakDuration,
        sessionsBeforeLongBreak = sessionsBeforeLongBreak,
        notificationsEnabled = notificationsEnabled,
        vibrationEnabled = vibrationEnabled,
        soundEnabled = soundEnabled,
        volume = volume,
        theme = theme,
        autoStartBreak = autoStartBreak
    )
}

data class SettingsUiState(
    val focusDuration: Int = 25,
    val shortBreakDuration: Int = 5,
    val longBreakDuration: Int = 15,
    val sessionsBeforeLongBreak: Int = 4,
    val notificationsEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val volume: Float = 1.0f,
    val theme: Int = 0,
    val autoStartBreak: Boolean = false
) 