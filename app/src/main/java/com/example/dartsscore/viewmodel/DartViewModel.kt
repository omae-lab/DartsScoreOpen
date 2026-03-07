package com.example.dartsscore

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class DartViewModel : ViewModel() {

    private val _currentRoundHits = mutableStateListOf<DartHit>()
    val currentRoundHits: List<DartHit> = _currentRoundHits

    private val _roundHistory = mutableStateListOf<List<DartHit>>()
    val roundHistory: List<List<DartHit>> = _roundHistory

    private val _turnFinished = mutableStateOf(false)
    val turnFinished: State<Boolean> = _turnFinished

    private val _totalScore = mutableStateOf(0)
    val totalScore: State<Int> = _totalScore

    private val _totalDarts = mutableStateOf(0)
    val totalDarts: State<Int> = _totalDarts


    val average: Float
        get() =
            if (_totalDarts.value == 0) 0f
            else (_totalScore.value.toFloat() / _totalDarts.value) * 3f


    fun addHit(hit: DartHit) {

        if (_turnFinished.value) return

        _currentRoundHits.add(hit)

        _totalScore.value += hit.score
        _totalDarts.value += 1

        if (_currentRoundHits.size == 3) {
            _turnFinished.value = true
        }
    }


    fun finishTurn() {

        if (_currentRoundHits.isEmpty()) return

        _roundHistory.add(_currentRoundHits.toList())

        _currentRoundHits.clear()

        _turnFinished.value = false
    }


    fun undoLastHit() {

        _turnFinished.value = false

        if (_currentRoundHits.isNotEmpty()) {

            val last = _currentRoundHits.removeLast()

            _totalScore.value -= last.score
            _totalDarts.value -= 1

            return
        }

        if (_roundHistory.isNotEmpty()) {

            val lastRound = _roundHistory.removeLast()

            _currentRoundHits.addAll(lastRound)

            val lastHit = _currentRoundHits.removeLast()

            _totalScore.value -= lastHit.score
            _totalDarts.value -= 1
        }
    }


    val hitHistory: List<androidx.compose.ui.geometry.Offset>
        get() =
            (_roundHistory.flatten() + _currentRoundHits)
                .map { it.offset }
}