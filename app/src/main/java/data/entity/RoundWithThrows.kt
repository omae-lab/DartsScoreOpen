package com.example.dartsscore.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoundWithThrows(
    @Embedded val round: Round,
    @Relation(
        parentColumn = "roundNumber",
        entityColumn = "roundNumber",
        entity = Throw::class
    )
    val throws: List<Throw>
)