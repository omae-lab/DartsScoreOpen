package com.example.dartsscore.data.database

import androidx.room.TypeConverter
import com.example.dartsscore.data.entity.GameType

class GameTypeConverter {
    @TypeConverter
    fun fromGameType(type: GameType): String = type.name

    @TypeConverter
    fun toGameType(name: String): GameType = GameType.valueOf(name)
}