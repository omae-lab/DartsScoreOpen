package com.example.dartsscore.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["gameId", "playerId"])
data class GamePlayer(
    val gameId: Long,
    val playerId: Long,
    val orderIndex: Int
)