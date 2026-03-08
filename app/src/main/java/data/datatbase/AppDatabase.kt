package com.example.dartsscore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dartsscore.data.dao.*
import com.example.dartsscore.data.entity.*

@Database(
    entities = [Player::class, Game::class, GamePlayer::class, Round::class, Throw::class],
    version = 1
)
@TypeConverters(GameTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun gameDao(): GameDao
    abstract fun throwDao(): ThrowDao
}