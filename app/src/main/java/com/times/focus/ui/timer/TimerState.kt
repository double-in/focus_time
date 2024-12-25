package com.times.focus.ui.timer

sealed class TimerState {
    object Idle : TimerState()
    object Running : TimerState()
    object Paused : TimerState()
    sealed class Finished : TimerState() {
        object Focus : Finished()
        object ShortBreak : Finished()
        object LongBreak : Finished()
    }
}

enum class TimerType {
    FOCUS,
    SHORT_BREAK,
    LONG_BREAK
} 