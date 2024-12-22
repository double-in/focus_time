package com.times.foucse_i.ui.timer

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.times.foucse_i.data.preferences.FocusPreferences
import com.times.foucse_i.data.repository.FocusRepository
import com.times.foucse_i.util.SoundUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor(
    private val repository: FocusRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var timerJob: Job? = null
    private var initialDuration: Long = FocusPreferences.DEFAULT_FOCUS_MINUTES * 60L
    private var remainingTime: Long = initialDuration

    private val _uiState = MutableLiveData<TimerUiState>()
    val uiState: LiveData<TimerUiState> = _uiState

    init {
        _uiState.value = TimerUiState()
        loadTotalTrees()
    }

    private fun loadTotalTrees() {
        viewModelScope.launch {
            try {
                val totalTrees = repository.getTreesPlanted()
                _uiState.value = _uiState.value?.copy(
                    totalTrees = totalTrees
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value?.copy(
                    totalTrees = 0
                )
            }
        }
    }

    fun setFocusMinutes(minutes: Int) {
        if (_uiState.value?.timerState is TimerState.Idle) {
            _uiState.value = _uiState.value?.copy(
                focusMinutes = minutes,
                remainingTime = minutes * 60L
            )
        }
    }

    fun startTimer() {
        if (timerJob?.isActive == true) return

        val currentState = _uiState.value ?: return
        if (currentState.timerState is TimerState.Paused) {
            resumeTimer()
            return
        }

        initialDuration = currentState.focusMinutes * 60L
        remainingTime = initialDuration

        _uiState.value = currentState.copy(
            timerState = TimerState.Running,
            remainingTime = remainingTime
        )

        timerJob = viewModelScope.launch {
            while (remainingTime > 0) {
                updateProgress()
                delay(1000)
                remainingTime--
            }
            onTimerComplete()
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _uiState.value = _uiState.value?.copy(
            timerState = TimerState.Paused
        )
    }

    private fun resumeTimer() {
        if (remainingTime > 0) {
            _uiState.value = _uiState.value?.copy(
                timerState = TimerState.Running
            )
            
            timerJob = viewModelScope.launch {
                while (remainingTime > 0) {
                    updateProgress()
                    delay(1000)
                    remainingTime--
                }
                onTimerComplete()
            }
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        saveSession(completed = false)
        resetTimer()
    }

    private fun onTimerComplete() {
        val currentState = _uiState.value ?: return
        
        when (currentState.timerType) {
            TimerType.FOCUS -> {
                SoundUtil.playTimerCompleteSound(context)
                saveSession(completed = true)
                _uiState.value = currentState.copy(
                    timerState = TimerState.Finished.Focus
                )
            }
            TimerType.SHORT_BREAK -> {
                _uiState.value = currentState.copy(
                    timerState = TimerState.Finished.ShortBreak
                )
            }
            TimerType.LONG_BREAK -> {
                _uiState.value = currentState.copy(
                    timerState = TimerState.Finished.LongBreak
                )
            }
        }
        
        resetTimer()
        loadTotalTrees()
    }

    private fun saveSession(completed: Boolean) {
        viewModelScope.launch {
            val duration = initialDuration - remainingTime
            repository.saveFocusSession(
                duration = duration,
                completed = completed,
                treeGrowthState = _uiState.value?.treeState ?: TreeGrowthState.SEED
            )
        }
    }

    private fun resetTimer() {
        remainingTime = 0
        initialDuration = 0
        _uiState.value = _uiState.value?.copy(
            timerState = TimerState.Idle,
            remainingTime = FocusPreferences.DEFAULT_FOCUS_MINUTES * 60L,
            progress = 0f,
            treeState = TreeGrowthState.SEED
        )
    }

    private fun updateProgress() {
        val progress = 1f - (remainingTime.toFloat() / initialDuration.toFloat())
        val treeState = when {
            progress >= 0.8f -> TreeGrowthState.BLOOMING
            progress >= 0.6f -> TreeGrowthState.MATURE
            progress >= 0.4f -> TreeGrowthState.GROWING
            progress >= 0.2f -> TreeGrowthState.SAPLING
            progress > 0f -> TreeGrowthState.SPROUT
            else -> TreeGrowthState.SEED
        }

        _uiState.value = _uiState.value?.copy(
            remainingTime = remainingTime,
            progress = progress,
            treeState = treeState
        )
    }
} 