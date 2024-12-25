package com.times.focus.ui.stats

data class StatsData(
    val totalFocusTime: Long = 0,
    val weeklyFocusTime: Long = 0,
    val monthlyFocusTime: Long = 0,
    val averageFocusTime: Long = 0,
    val totalSessions: Int = 0,
    val todaySessions: Int = 0,
    val treesPlanted: Int = 0,
    val longestSession: Long = 0,
    val mostProductiveDay: Int = 0,
    val seedTrees: Int = 0,
    val sproutTrees: Int = 0,
    val saplingTrees: Int = 0,
    val growingTrees: Int = 0,
    val matureTrees: Int = 0,
    val bloomingTrees: Int = 0
) 