package com.example.dartsscore.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Throw(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val roundNumber: Int,
    val dartIndex: Int,
    val segment: Int,
    val multiplier: Int,
    val hitX: Float,
    val hitY: Float,
    val timestamp: Long
)