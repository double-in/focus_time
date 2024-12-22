package com.times.foucse_i.data.repository

import com.times.foucse_i.data.db.dao.FocusSessionDao
import com.times.foucse_i.data.db.entity.FocusSession
import com.times.foucse_i.ui.timer.TreeGrowthState
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FocusRepository @Inject constructor(
    private val focusSessionDao: FocusSessionDao
) {
    suspend fun saveFocusSession(
        duration: Long,
        completed: Boolean,
        treeGrowthState: TreeGrowthState
    ) {
        val session = FocusSession(
            startTime = System.currentTimeMillis(),
            duration = duration,
            completed = completed,
            treeGrowthState = treeGrowthState
        )
        focusSessionDao.insert(session)
    }

    suspend fun getTotalFocusTime(): Long {
        return try {
            focusSessionDao.getTotalFocusTime()
        } catch (e: Exception) {
            0L
        }
    }

    suspend fun getTreesPlanted(): Int {
        return try {
            focusSessionDao.getCompletedSessionsCount()
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getRecentSessions(limit: Int = 10): List<FocusSession> {
        return try {
            focusSessionDao.getRecentSessions(limit)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getTodaySessionsCount(): Int {
        return try {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            val startOfDay = calendar.timeInMillis
            
            focusSessionDao.getSessionsCountAfter(startOfDay)
        } catch (e: Exception) {
            0
        }
    }

    suspend fun getTotalSessionsCount(): Int {
        return try {
            focusSessionDao.getTotalSessionsCount()
        } catch (e: Exception) {
            0
        }
    }
} 