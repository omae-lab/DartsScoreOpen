package com.example.dartsscore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dartsscore.data.dao.*
import com.example.dartsscore.data.entity.*

@Database(
    entities = [Player::class, Game::class, Throw::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun gameDao(): GameDao
    abstract fun throwDao(): ThrowDao
}