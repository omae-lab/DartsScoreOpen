package com.example.dartsscore.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "darts_database"
            )
                .fallbackToDestructiveMigration()
                //★開発中のみ。完成したら削除しないとデータ飛ぶ可能性あり。完成したらMigrationを行う。
                .build()
            INSTANCE = instance
            instance
        }
    }
}