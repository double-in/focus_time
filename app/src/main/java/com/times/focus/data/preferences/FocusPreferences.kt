package com.times.focus.data.preferences

import androidx.appcompat.app.AppCompatDelegate

object FocusPreferences {
    // Timer settings
    const val MIN_FOCUS_MINUTES = 1
    const val MAX_FOCUS_MINUTES = 120
    const val DEFAULT_FOCUS_MINUTES = 25

    const val MIN_BREAK_MINUTES = 1
    const val MAX_BREAK_MINUTES = 30
    const val DEFAULT_SHORT_BREAK_MINUTES = 5
    const val DEFAULT_LONG_BREAK_MINUTES = 15

    const val MIN_POMODOROS = 2
    const val MAX_POMODOROS = 6
    const val DEFAULT_POMODOROS_BEFORE_LONG_BREAK = 4

    // Theme settings
    const val DEFAULT_THEME = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

    // Notification settings
    const val DEFAULT_NOTIFICATIONS_ENABLED = true
    const val DEFAULT_VIBRATION_ENABLED = true
    const val DEFAULT_SOUND_ENABLED = true
    const val DEFAULT_VOLUME = 1.0f
    const val DEFAULT_AUTO_START_BREAK = false

    // Preference keys
    const val PREFS_NAME = "focus_timer_preferences"
    const val KEY_FOCUS_DURATION = "focus_duration"
    const val KEY_SHORT_BREAK_DURATION = "short_break_duration"
    const val KEY_LONG_BREAK_DURATION = "long_break_duration"
    const val KEY_SESSIONS_BEFORE_LONG_BREAK = "sessions_before_long_break"
    const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
    const val KEY_VIBRATION_ENABLED = "vibration_enabled"
    const val KEY_SOUND_ENABLED = "sound_enabled"
    const val KEY_VOLUME = "volume"
    const val KEY_THEME = "theme"
    const val KEY_AUTO_START_BREAK = "auto_start_break"
} 