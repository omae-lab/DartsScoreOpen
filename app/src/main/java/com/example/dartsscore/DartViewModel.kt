package com.example.dartsscore

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import kotlin.collections.addAll
import kotlin.collections.minusAssign
import kotlin.collections.removeLast

class DartViewModel : ViewModel() {

    private val _currentScore = mutableStateOf(0)
    val currentScore: State<Int> = _currentScore

    private val _currentRoundHits = mutableStateListOf<DartHit>()
    val currentRoundHits: List<DartHit> = _currentRoundHits

    private val _roundHistory = mutableStateListOf<List<DartHit>>()
    val roundHistory: List<List<DartHit>> = _roundHistory

    fun addHit(hit: DartHit) {
        _currentRoundHits.add(hit)
        _currentScore.value += hit.score

        if (_currentRoundHits.size == 3) {
            _roundHistory.add(_currentRoundHits.toList())
            _currentRoundHits.clear()
        }
    }
    fun undoLastHit() {

        if (_currentRoundHits.isEmpty() && _roundHistory.isEmpty()) {
            return
        }

        // ケース1：今のラウンドにヒットがある
        if (_currentRoundHits.isNotEmpty()) {
            val lastHit = _currentRoundHits.removeLast()
            _currentScore.value -= lastHit.score
            return
        }

        // ケース2：ラウンド確定後
        if (_roundHistory.isNotEmpty()) {

            val lastRound = _roundHistory.removeLast()

            // 3投を現在ラウンドに戻す
            _currentRoundHits.addAll(lastRound)

            // その中の最後の1投を削除
            val lastHit = _currentRoundHits.removeLast()
            _currentScore.value -= lastHit.score
        }
    }
}

