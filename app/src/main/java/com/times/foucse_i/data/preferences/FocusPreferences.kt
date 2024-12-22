package com.times.foucse_i.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Singleton
class FocusPreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private val dataStore = context.dataStore

    val focusMinutes: Flow<Int> = dataStore.data.map { preferences ->
        preferences[FOCUS_MINUTES_KEY] ?: DEFAULT_FOCUS_MINUTES
    }

    val shortBreakMinutes: Flow<Int> = dataStore.data.map { preferences ->
        preferences[SHORT_BREAK_MINUTES_KEY] ?: DEFAULT_SHORT_BREAK_MINUTES
    }

    val longBreakMinutes: Flow<Int> = dataStore.data.map { preferences ->
        preferences[LONG_BREAK_MINUTES_KEY] ?: DEFAULT_LONG_BREAK_MINUTES
    }

    val pomodorosBeforeLongBreak: Flow<Int> = dataStore.data.map { preferences ->
        preferences[POMODOROS_BEFORE_LONG_BREAK_KEY] ?: DEFAULT_POMODOROS_BEFORE_LONG_BREAK
    }

    val autoStartBreak: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[AUTO_START_BREAK_KEY] ?: DEFAULT_AUTO_START_BREAK
    }

    suspend fun setFocusMinutes(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[FOCUS_MINUTES_KEY] = minutes.coerceIn(MIN_FOCUS_MINUTES, MAX_FOCUS_MINUTES)
        }
    }

    suspend fun setShortBreakMinutes(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[SHORT_BREAK_MINUTES_KEY] = minutes.coerceIn(MIN_BREAK_MINUTES, MAX_BREAK_MINUTES)
        }
    }

    suspend fun setLongBreakMinutes(minutes: Int) {
        dataStore.edit { preferences ->
            preferences[LONG_BREAK_MINUTES_KEY] = minutes.coerceIn(MIN_BREAK_MINUTES, MAX_BREAK_MINUTES)
        }
    }

    suspend fun setPomodorosBeforeLongBreak(count: Int) {
        dataStore.edit { preferences ->
            preferences[POMODOROS_BEFORE_LONG_BREAK_KEY] = count.coerceIn(MIN_POMODOROS, MAX_POMODOROS)
        }
    }

    suspend fun setAutoStartBreak(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[AUTO_START_BREAK_KEY] = enabled
        }
    }

    companion object {
        val FOCUS_MINUTES_KEY = intPreferencesKey("focus_minutes")
        val SHORT_BREAK_MINUTES_KEY = intPreferencesKey("short_break_minutes")
        val LONG_BREAK_MINUTES_KEY = intPreferencesKey("long_break_minutes")
        val POMODOROS_BEFORE_LONG_BREAK_KEY = intPreferencesKey("pomodoros_before_long_break")
        val AUTO_START_BREAK_KEY = booleanPreferencesKey("auto_start_break")

        const val DEFAULT_FOCUS_MINUTES = 25
        const val DEFAULT_SHORT_BREAK_MINUTES = 5
        const val DEFAULT_LONG_BREAK_MINUTES = 15
        const val DEFAULT_POMODOROS_BEFORE_LONG_BREAK = 4
        const val DEFAULT_AUTO_START_BREAK = false

        const val MIN_FOCUS_MINUTES = 1
        const val MAX_FOCUS_MINUTES = 120
        const val MIN_BREAK_MINUTES = 1
        const val MAX_BREAK_MINUTES = 30
        const val MIN_POMODOROS = 2
        const val MAX_POMODOROS = 6
    }
} 