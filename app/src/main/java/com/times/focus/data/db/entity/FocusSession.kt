package com.times.focus.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.times.focus.ui.timer.TreeGrowthState

@Entity(tableName = "focus_sessions")
data class FocusSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val duration: Long,
    val completed: Boolean,
    val treeGrowthState: TreeGrowthState
) 