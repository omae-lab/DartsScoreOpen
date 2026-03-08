package com.example.dartsscore.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GameFullHistory(
    @Embedded val game: Game,
    @Relation(
        entity = Player::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = androidx.room.Junction(
            value = GamePlayer::class,
            parentColumn = "gameId",
            entityColumn = "playerId"
        )
    )
    val players: List<PlayerWithRounds>
)