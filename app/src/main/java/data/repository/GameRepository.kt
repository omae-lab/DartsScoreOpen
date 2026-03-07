package com.example.dartsscore.data.repository

import com.example.dartsscore.data.dao.ThrowDao
import com.example.dartsscore.data.entity.Throw
import kotlinx.coroutines.flow.Flow

class GameRepository(private val throwDao: ThrowDao) {

    suspend fun addThrow(throwData: Throw) = throwDao.insertThrow(throwData)

    fun getThrowsFlow(gameId: Long, playerId: Long): Flow<List<Throw>> =
        throwDao.getThrowsFlow(gameId, playerId)

    suspend fun getLastThrow(gameId: Long, playerId: Long): Throw? =
        throwDao.getLastThrow(gameId, playerId)
}