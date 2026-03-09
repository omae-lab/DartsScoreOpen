package com.example.dartsscore.data.dao

import androidx.room.*
import com.example.dartsscore.data.entity.Throw
import kotlinx.coroutines.flow.Flow

@Dao
interface ThrowDao {
    @Insert
    suspend fun insertThrow(throwData: Throw)

    @Query("SELECT * FROM Throw WHERE gameId = :gameId AND playerId = :playerId ORDER BY timestamp ASC")
    fun getThrowsFlow(gameId: Long, playerId: Long): Flow<List<Throw>>

    @Query("SELECT * FROM Throw WHERE gameId = :gameId AND playerId = :playerId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastThrow(gameId: Long, playerId: Long): Throw?

    @Query("SELECT * FROM Throw WHERE gameId = :gameId AND playerId = :playerId AND roundNumber = :roundNumber ORDER BY dartIndex ASC")
    suspend fun getThrowsByRound(gameId: Long, playerId: Long, roundNumber: Int): List<Throw>

    @Delete
    suspend fun deleteThrow(throwData: Throw)

    @Query("""
        SELECT AVG(score)
        FROM Throw
        WHERE playerId = :playerId
    """)
    suspend fun getPlayerAverage(playerId: Long): Double?

    @Query("""
        SELECT COUNT(*)
        FROM Throw
        WHERE playerId = :playerId
    """)
    suspend fun getThrowCount(playerId: Long): Int

    @Query("""
        SELECT COUNT(*)
        FROM Throw
        WHERE playerId = :playerId
        AND bed = :bed
    """)
    suspend fun countHitsByBed(
        playerId: Long,
        bed: String
    ): Int

}