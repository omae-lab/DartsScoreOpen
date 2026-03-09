package com.example.dartsscore.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(entity = Game::class, parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Player::class, parentColumns = ["id"], childColumns = ["playerId"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Throw(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameId: Long,
    val playerId: Long,
    val roundNumber: Int,
    val dartIndex: Int,
    val segment: Int,
    val multiplier: Int,
    val score: Int,
    val bed: String,
    val hitX: Float,
    val hitY: Float,
    val timestamp: Long
)