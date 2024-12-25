package com.times.foucse_i.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.times.foucse_i.ui.timer.TreeGrowthState

@Entity(tableName = "focus_sessions")
data class FocusSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val duration: Long,
    val completed: Boolean,
    val treeGrowthState: TreeGrowthState
) 