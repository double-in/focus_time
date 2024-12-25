package com.times.focus.ui.timer

import com.times.focus.data.preferences.FocusPreferences

data class TimerUiState(
    val timerState: TimerState = TimerState.Idle,
    val remainingTime: Long = FocusPreferences.DEFAULT_FOCUS_MINUTES * 60L,
    val progress: Float = 1f,
    val focusMinutes: Int = FocusPreferences.DEFAULT_FOCUS_MINUTES,
    val timerType: TimerType = TimerType.FOCUS,
    val completedPomodoros: Int = 0,
    val treeState: TreeGrowthState = TreeGrowthState.SEED,
    val totalTrees: Int = 0,
    val notificationsEnabled: Boolean = FocusPreferences.DEFAULT_NOTIFICATIONS_ENABLED,
    val vibrationEnabled: Boolean = FocusPreferences.DEFAULT_VIBRATION_ENABLED,
    val soundEnabled: Boolean = FocusPreferences.DEFAULT_SOUND_ENABLED,
    val volume: Float = FocusPreferences.DEFAULT_VOLUME
) 