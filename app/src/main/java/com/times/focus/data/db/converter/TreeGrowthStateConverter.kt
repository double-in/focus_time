package com.times.focus.data.db.converter

import androidx.room.TypeConverter
import com.times.focus.ui.timer.TreeGrowthState

class TreeGrowthStateConverter {
    @TypeConverter
    fun toTreeGrowthState(value: String?): TreeGrowthState? {
        return value?.let { TreeGrowthState.valueOf(it) }
    }

    @TypeConverter
    fun fromTreeGrowthState(state: TreeGrowthState?): String? {
        return state?.name
    }
} 