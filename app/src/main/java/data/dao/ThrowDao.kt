package com.example.dartsscore.data.dao

import androidx.room.*
import com.example.dartsscore.data.entity.Throw
import kotlinx.coroutines.flow.Flow

@Dao
interface ThrowDao {
    @Insert
    suspend fun insertThrow(throwData: Throw)

    @Query("""
        SELECT * FROM Throw
        WHERE gameId = :gameId AND playerId = :playerId
        ORDER BY timestamp ASC
    """)
    fun getThrowsFlow(gameId: Long, playerId: Long): Flow<List<Throw>>

    @Query("""
        SELECT * FROM Throw
        WHERE gameId = :gameId AND playerId = :playerId
        ORDER BY timestamp DESC
        LIMIT 1
    """)
    suspend fun getLastThrow(gameId: Long, playerId: Long): Throw?

    @Delete
    suspend fun deleteThrow(throwData: Throw)
}