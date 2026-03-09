package com.example.dartsscore.data.repository

import com.example.dartsscore.data.dao.PlayerDao
import com.example.dartsscore.data.entity.Player

class PlayerRepository(
    private val playerDao: PlayerDao
) {

    suspend fun insertPlayer(name: String) {
        val player = Player(
            name = name,
            createdAt = System.currentTimeMillis()
        )
        playerDao.insert(player)
    }

    suspend fun getPlayers(): List<Player> {
        return playerDao.getAll()
    }
}