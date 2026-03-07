package com.example.dartsscore.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameType: GameType,
    val startTime: Long,
    val endTime: Long? = null
)