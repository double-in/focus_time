package com.times.foucse_i.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.times.foucse_i.data.db.converter.DateConverter
import com.times.foucse_i.data.db.converter.TreeGrowthStateConverter
import com.times.foucse_i.data.db.dao.FocusSessionDao
import com.times.foucse_i.data.db.entity.FocusSession

@Database(
    entities = [FocusSession::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, TreeGrowthStateConverter::class)
abstract class FocusDatabase : RoomDatabase() {
    abstract fun focusSessionDao(): FocusSessionDao

    companion object {
        @Volatile
        private var INSTANCE: FocusDatabase? = null

        fun getDatabase(context: Context): FocusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FocusDatabase::class.java,
                    "focus_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 