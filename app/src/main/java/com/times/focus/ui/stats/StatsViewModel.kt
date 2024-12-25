package com.times.focus.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.times.focus.data.db.entity.FocusSession
import com.times.focus.data.repository.FocusRepository
import com.times.focus.ui.timer.TreeGrowthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: FocusRepository
) : ViewModel() {

    private val _statsData = MutableLiveData<StatsData>()
    val statsData: LiveData<StatsData> = _statsData

    private val _recentSessions = MutableLiveData<List<FocusSession>>()
    val recentSessions: LiveData<List<FocusSession>> = _recentSessions

    init {
        loadStats()
    }

    fun loadStats() {
        viewModelScope.launch {
            val totalFocusTime = repository.getTotalFocusTime()
            val weeklyFocusTime = repository.getWeeklyFocusTime()
            val monthlyFocusTime = repository.getMonthlyFocusTime()
            val averageFocusTime = repository.getAverageFocusTime()
            val totalSessions = repository.getTotalSessionsCount()
            val todaySessions = repository.getTodaySessionsCount()
            val treesPlanted = repository.getTreesPlanted()
            val longestSession = repository.getLongestSession()
            val mostProductiveDay = repository.getMostProductiveDay()

            // 获取每种状态的树木数量
            val seedTrees = repository.getTreeCountByState(TreeGrowthState.SEED)
            val sproutTrees = repository.getTreeCountByState(TreeGrowthState.SPROUT)
            val saplingTrees = repository.getTreeCountByState(TreeGrowthState.SAPLING)
            val growingTrees = repository.getTreeCountByState(TreeGrowthState.GROWING)
            val matureTrees = repository.getTreeCountByState(TreeGrowthState.MATURE)
            val bloomingTrees = repository.getTreeCountByState(TreeGrowthState.BLOOMING)

            _statsData.value = StatsData(
                totalFocusTime = totalFocusTime,
                weeklyFocusTime = weeklyFocusTime,
                monthlyFocusTime = monthlyFocusTime,
                averageFocusTime = averageFocusTime,
                totalSessions = totalSessions,
                todaySessions = todaySessions,
                treesPlanted = treesPlanted,
                longestSession = longestSession,
                mostProductiveDay = mostProductiveDay,
                seedTrees = seedTrees,
                sproutTrees = sproutTrees,
                saplingTrees = saplingTrees,
                growingTrees = growingTrees,
                matureTrees = matureTrees,
                bloomingTrees = bloomingTrees
            )

            _recentSessions.value = repository.getRecentSessions()
        }
    }

    fun formatDuration(minutes: Long): String {
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return when {
            hours > 0 -> "$hours h $remainingMinutes min"
            else -> "$remainingMinutes min"
        }
    }
} 