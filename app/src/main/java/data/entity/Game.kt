package com.example.dartsscore.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.dartsscore.data.database.GameTypeConverter

@Entity
@TypeConverters(GameTypeConverter::class)
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gameType: GameType,
    val startTime: Long,
    val endTime: Long? = null
)