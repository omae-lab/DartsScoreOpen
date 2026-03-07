package com.example.dartsscore.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Round(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val roundNumber: Int
)