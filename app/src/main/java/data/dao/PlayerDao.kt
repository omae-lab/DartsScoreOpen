package com.example.dartsscore.data.dao

import androidx.room.*
import com.example.dartsscore.data.entity.Player

@Dao
interface PlayerDao {
    @Insert
    suspend fun insert(player: Player): Long

    @Query("SELECT * FROM Player")
    suspend fun getAll(): List<Player>
}