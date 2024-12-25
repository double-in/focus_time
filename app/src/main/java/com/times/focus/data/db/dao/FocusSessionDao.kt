package com.times.focus.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.times.focus.data.db.entity.FocusSession
import com.times.focus.ui.timer.TreeGrowthState

@Dao
interface FocusSessionDao {
    @Insert
    suspend fun insert(session: FocusSession)

    @Query("SELECT COALESCE(SUM(duration), 0) FROM focus_sessions WHERE completed = 1")
    suspend fun getTotalFocusTime(): Long

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE completed = 1")
    suspend fun getCompletedSessionsCount(): Int

    @Query("SELECT * FROM focus_sessions ORDER BY startTime DESC LIMIT :limit")
    suspend fun getRecentSessions(limit: Int): List<FocusSession>

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE startTime >= :startTime")
    suspend fun getSessionsCountAfter(startTime: Long): Int

    @Query("SELECT COUNT(*) FROM focus_sessions")
    suspend fun getTotalSessionsCount(): Int

    @Query("SELECT COALESCE(SUM(duration), 0) FROM focus_sessions WHERE completed = 1 AND startTime >= :startTime")
    suspend fun getFocusTimeAfter(startTime: Long): Long

    @Query("SELECT COALESCE(AVG(duration), 0) FROM focus_sessions WHERE completed = 1")
    suspend fun getAverageFocusTime(): Long

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE completed = 1 AND treeGrowthState = :state")
    suspend fun getTreeCountByState(state: TreeGrowthState): Int

    @Query("SELECT MAX(duration) FROM focus_sessions WHERE completed = 1")
    suspend fun getLongestSession(): Long

    @Query("SELECT COUNT(*) FROM focus_sessions WHERE completed = 1 GROUP BY date(startTime/1000, 'unixepoch') ORDER BY COUNT(*) DESC LIMIT 1")
    suspend fun getMostProductiveDay(): Int
} 