package com.example.dartsscore.data.dao

import androidx.room.*
import com.example.dartsscore.data.entity.*

@Dao
interface GameDao {

    @Insert
    suspend fun insert(game: Game): Long

    @Query("SELECT * FROM Game ORDER BY startTime DESC")
    suspend fun getGames(): List<Game>

    @Query("SELECT * FROM Game WHERE id = :gameId")
    suspend fun getGameById(gameId: Long): Game?

    @Query("UPDATE Game SET endTime = :endTime WHERE id = :gameId")
    suspend fun updateEndTime(gameId: Long, endTime: Long)

    // 全履歴取得
    @Transaction
    @Query("SELECT * FROM Game WHERE id = :gameId")
    suspend fun getGameFullHistory(gameId: Long): GameFullHistory
}