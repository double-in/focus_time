package com.times.focus.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = FocusPreferences.PREFS_NAME)

@Singleton
class PreferencesRepository @Inject constructor(@ApplicationContext private val context: Context) {
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val FOCUS_DURATION = intPreferencesKey(FocusPreferences.KEY_FOCUS_DURATION)
        val SHORT_BREAK_DURATION = intPreferencesKey(FocusPreferences.KEY_SHORT_BREAK_DURATION)
        val LONG_BREAK_DURATION = intPreferencesKey(FocusPreferences.KEY_LONG_BREAK_DURATION)
        val SESSIONS_BEFORE_LONG_BREAK = intPreferencesKey(FocusPreferences.KEY_SESSIONS_BEFORE_LONG_BREAK)
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey(FocusPreferences.KEY_NOTIFICATIONS_ENABLED)
        val VIBRATION_ENABLED = booleanPreferencesKey(FocusPreferences.KEY_VIBRATION_ENABLED)
        val SOUND_ENABLED = booleanPreferencesKey(FocusPreferences.KEY_SOUND_ENABLED)
        val VOLUME = floatPreferencesKey(FocusPreferences.KEY_VOLUME)
        val THEME = intPreferencesKey(FocusPreferences.KEY_THEME)
        val AUTO_START_BREAK = booleanPreferencesKey(FocusPreferences.KEY_AUTO_START_BREAK)
    }

    val focusDuration: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.FOCUS_DURATION] ?: FocusPreferences.DEFAULT_FOCUS_MINUTES
    }

    val shortBreakDuration: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SHORT_BREAK_DURATION] ?: FocusPreferences.DEFAULT_SHORT_BREAK_MINUTES
    }

    val longBreakDuration: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.LONG_BREAK_DURATION] ?: FocusPreferences.DEFAULT_LONG_BREAK_MINUTES
    }

    val sessionsBeforeLongBreak: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SESSIONS_BEFORE_LONG_BREAK] ?: FocusPreferences.DEFAULT_POMODOROS_BEFORE_LONG_BREAK
    }

    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] ?: FocusPreferences.DEFAULT_NOTIFICATIONS_ENABLED
    }

    val vibrationEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.VIBRATION_ENABLED] ?: FocusPreferences.DEFAULT_VIBRATION_ENABLED
    }

    val soundEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SOUND_ENABLED] ?: FocusPreferences.DEFAULT_SOUND_ENABLED
    }

    val volume: Flow<Float> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.VOLUME] ?: FocusPreferences.DEFAULT_VOLUME
    }

    val theme: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.THEME] ?: FocusPreferences.DEFAULT_THEME
    }

    val autoStartBreak: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.AUTO_START_BREAK] ?: FocusPreferences.DEFAULT_AUTO_START_BREAK
    }

    suspend fun setFocusDuration(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FOCUS_DURATION] = minutes.coerceIn(
                FocusPreferences.MIN_FOCUS_MINUTES,
                FocusPreferences.MAX_FOCUS_MINUTES
            )
        }
    }

    suspend fun setShortBreakDuration(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHORT_BREAK_DURATION] = minutes.coerceIn(
                FocusPreferences.MIN_BREAK_MINUTES,
                FocusPreferences.MAX_BREAK_MINUTES
            )
        }
    }

    suspend fun setLongBreakDuration(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LONG_BREAK_DURATION] = minutes.coerceIn(
                FocusPreferences.MIN_BREAK_MINUTES,
                FocusPreferences.MAX_BREAK_MINUTES
            )
        }
    }

    suspend fun setSessionsBeforeLongBreak(count: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SESSIONS_BEFORE_LONG_BREAK] = count.coerceIn(
                FocusPreferences.MIN_POMODOROS,
                FocusPreferences.MAX_POMODOROS
            )
        }
    }

    suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun setVibrationEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.VIBRATION_ENABLED] = enabled
        }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SOUND_ENABLED] = enabled
        }
    }

    suspend fun setVolume(volume: Float) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.VOLUME] = volume.coerceIn(0f, 1f)
        }
    }

    suspend fun setTheme(theme: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = theme
        }
    }

    suspend fun setAutoStartBreak(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.AUTO_START_BREAK] = enabled
        }
    }
} 