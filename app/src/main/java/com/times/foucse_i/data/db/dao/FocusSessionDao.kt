package com.times.foucse_i.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.times.foucse_i.data.db.entity.FocusSession

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
} 