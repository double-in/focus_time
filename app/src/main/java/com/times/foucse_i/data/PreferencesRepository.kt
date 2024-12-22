package com.times.foucse_i.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun isSoundEnabled(): Boolean {
        return prefs.getBoolean(KEY_SOUND_ENABLED, true)
    }

    fun setSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply()
    }

    fun getVolume(): Float {
        return prefs.getFloat(KEY_VOLUME, 1.0f)
    }

    fun setVolume(volume: Float) {
        prefs.edit().putFloat(KEY_VOLUME, volume).apply()
    }

    fun getCurrentTheme(): Int {
        return prefs.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    fun setTheme(theme: Int) {
        prefs.edit().putInt(KEY_THEME, theme).apply()
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    companion object {
        private const val PREFS_NAME = "focus_timer_preferences"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_VOLUME = "volume"
        private const val KEY_THEME = "theme"
    }
} 