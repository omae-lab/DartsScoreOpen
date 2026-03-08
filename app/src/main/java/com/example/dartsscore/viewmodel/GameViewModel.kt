package com.example.dartsscore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dartsscore.data.entity.GameFullHistory
import com.example.dartsscore.data.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val repository: GameRepository) : ViewModel() {

    private val _gameFullHistory = MutableStateFlow<GameFullHistory?>(null)
    val gameFullHistory: StateFlow<GameFullHistory?> = _gameFullHistory

    // Flow を購読して StateFlow に反映
    fun loadGameFullHistory(gameId: Long) {
        viewModelScope.launch {
            repository.getGameFullHistoryFlow(gameId).collect { history ->
                _gameFullHistory.value = history
            }
        }
    }
}