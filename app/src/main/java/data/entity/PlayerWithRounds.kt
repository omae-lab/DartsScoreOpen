package com.example.dartsscore.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlayerWithRounds(
    @Embedded val player: Player,
    @Relation(
        parentColumn = "id",
        entityColumn = "playerId",
        entity = Round::class
    )
    val rounds: List<RoundWithThrows>
)