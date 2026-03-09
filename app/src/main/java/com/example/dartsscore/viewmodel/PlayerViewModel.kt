package com.example.dartsscore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dartsscore.data.database.DatabaseProvider
import com.example.dartsscore.data.entity.Player
import com.example.dartsscore.data.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context

class PlayerViewModel(
    private val context: Context
) : ViewModel() {

    private val database = DatabaseProvider.getDatabase(context)

    private val playerDao = database.playerDao()
    private val throwDao = database.throwDao()

    private val repository = PlayerRepository(playerDao)

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players

    fun loadPlayers() {
        viewModelScope.launch {
            _players.value = repository.getPlayers()
        }
    }

    fun addPlayer(name: String) {
        viewModelScope.launch {
            repository.insertPlayer(name)
            loadPlayers()
        }
    }

    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            playerDao.delete(player)
            loadPlayers()
        }
    }

    suspend fun getPlayerAverage(playerId: Long): Double? {
        return throwDao.getPlayerAverage(playerId)
    }

    suspend fun getThrowCount(playerId: Long): Int {
        return throwDao.getThrowCount(playerId)
    }

    suspend fun restorePlayer(player: Player) {
        playerDao.insert(player)
        loadPlayers()
    }

    suspend fun countHitsByBed(playerId: Long, bed: String): Int {
        return throwDao.countHitsByBed(playerId, bed)
    }
}