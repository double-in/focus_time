package com.times.foucse_i.ui.stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.times.foucse_i.data.db.entity.FocusSession
import com.times.foucse_i.data.repository.FocusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: FocusRepository
) : ViewModel() {

    private val _totalFocusTime = MutableLiveData<Long>()
    val totalFocusTime: LiveData<Long> = _totalFocusTime

    private val _treesPlanted = MutableLiveData<Int>()
    val treesPlanted: LiveData<Int> = _treesPlanted

    private val _recentSessions = MutableLiveData<List<FocusSession>>()
    val recentSessions: LiveData<List<FocusSession>> = _recentSessions

    init {
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _totalFocusTime.value = repository.getTotalFocusTime()
            _treesPlanted.value = repository.getTreesPlanted()
            _recentSessions.value = repository.getRecentSessions()
        }
    }

    fun refreshStats() {
        loadStats()
    }
} 