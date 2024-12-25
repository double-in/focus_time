package com.times.foucse_i.data.db.converter

import androidx.room.TypeConverter
import com.times.foucse_i.ui.timer.TreeGrowthState

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