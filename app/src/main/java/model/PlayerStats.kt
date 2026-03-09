package com.example.dartsscore.model

import com.example.dartsscore.data.entity.Player

data class PlayerStats(
    val player: Player,
    val average: Double?,
    val throwCount: Int
)