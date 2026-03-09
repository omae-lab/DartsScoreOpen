package com.example.dartsscore.data.dao

import androidx.room.*
import com.example.dartsscore.data.entity.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: Player): Long

    @Query("SELECT * FROM Player")
    suspend fun getAll(): List<Player>

    @Delete
    suspend fun delete(player: Player)
}