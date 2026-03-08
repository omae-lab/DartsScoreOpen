package com.example.dartsscore.data.repository

import com.example.dartsscore.data.dao.GameDao
import com.example.dartsscore.data.dao.ThrowDao
import com.example.dartsscore.data.entity.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val gameDao: GameDao,
    private val throwDao: ThrowDao
) {

    suspend fun addThrow(throwData: Throw) = throwDao.insertThrow(throwData)

    fun getThrowsFlow(gameId: Long, playerId: Long): Flow<List<Throw>> =
        throwDao.getThrowsFlow(gameId, playerId)

    suspend fun getLastThrow(gameId: Long, playerId: Long): Throw? =
        throwDao.getLastThrow(gameId, playerId)

    suspend fun getThrowsByRound(gameId: Long, playerId: Long, roundNumber: Int) =
        throwDao.getThrowsByRound(gameId, playerId, roundNumber)

    // 全履歴 Flow 取得
    fun getGameFullHistoryFlow(gameId: Long): Flow<GameFullHistory> = flow {
        val history = gameDao.getGameFullHistory(gameId)
        emit(history)
    }
}