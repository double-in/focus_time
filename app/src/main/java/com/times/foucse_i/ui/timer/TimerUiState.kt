package com.times.foucse_i.ui.timer

import com.times.foucse_i.data.preferences.FocusPreferences

data class TimerUiState(
    val timerState: TimerState = TimerState.Idle,
    val remainingTime: Long = FocusPreferences.DEFAULT_FOCUS_MINUTES * 60L,
    val progress: Float = 1f,
    val statistics: TimerStatistics = TimerStatistics(),
    val focusMinutes: Int = FocusPreferences.DEFAULT_FOCUS_MINUTES,
    val timerType: TimerType = TimerType.FOCUS,
    val completedPomodoros: Int = 0,
    val treeState: TreeGrowthState = TreeGrowthState.SEED,
    val totalTrees: Int = 0
) 