package com.example.dartsscore

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.dartsscore.data.entity.*
import com.example.dartsscore.engine.*
import ui.GameSettings

class DartViewModel : ViewModel() {

    var currentSettings: GameSettings? = null
        private set

    private var engine: GameEngine =
        GameEngineFactory.create(GameType.INFINITE_COUNT_UP)

    private val _currentRoundHits = mutableStateListOf<DartHit>()
    val currentRoundHits: List<DartHit> = _currentRoundHits

    private val _roundHistory = mutableStateListOf<List<DartHit>>()
    val roundHistory: List<List<DartHit>> = _roundHistory

    private val _turnFinished = mutableStateOf(false)
    val turnFinished: State<Boolean> = _turnFinished

    private val _totalScore = mutableStateOf(engine.initialScore)
    val totalScore: State<Int> = _totalScore

    private val _totalDarts = mutableStateOf(0)
    val totalDarts: State<Int> = _totalDarts

    val average: Float
        get() =
            if (_totalDarts.value == 0) 0f
            else (_totalScore.value.toFloat() / _totalDarts.value) * 3f

    private fun resetGame() {
        _currentRoundHits.clear()
        _roundHistory.clear()
        _totalScore.value = engine.initialScore
        _totalDarts.value = 0
        _turnFinished.value = false
    }

    // -------------------------
    // GameSettings を適用
    // -------------------------
    fun applySettings(settings: GameSettings) {
        currentSettings = settings

        val s = currentSettings
        engine = if (s != null && s.gameType == GameType.ZERO_ONE_301) {
            GameEngineFactory.create(
                gameType = s.gameType,
                initialScore = s.initialScore,
                inRule = s.inRule!!,
                outRule = s.outRule!!,
                bullRule = s.bullRule!!
            )
        } else {
            GameEngineFactory.create(s?.gameType ?: GameType.INFINITE_COUNT_UP)
        }

        resetGame()
    }

    fun startGame(gameType: GameType) {
        engine = GameEngineFactory.create(gameType)
        resetGame()
    }

    fun addHit(hit: DartHit) {
        if (_turnFinished.value) return

        val result = engine.addHit(
            hit,
            _currentRoundHits,
            _totalScore.value,
            _totalDarts.value
        )

        _currentRoundHits.clear()
        _currentRoundHits.addAll(result.roundHits)

        _totalScore.value = result.totalScore
        _totalDarts.value = result.totalDarts
        _turnFinished.value = result.turnFinished
    }

    fun finishTurn() {
        val result = engine.finishTurn(_currentRoundHits, _roundHistory)

        _currentRoundHits.clear()
        _currentRoundHits.addAll(result.roundHits)

        _roundHistory.clear()
        _roundHistory.addAll(result.roundHistory)

        _turnFinished.value = result.turnFinished
    }

    fun undoLastHit() {
        _turnFinished.value = false

        val result = engine.undoLastHit(
            _currentRoundHits,
            _roundHistory,
            _totalScore.value,
            _totalDarts.value
        )

        _currentRoundHits.clear()
        _currentRoundHits.addAll(result.roundHits)

        _roundHistory.clear()
        _roundHistory.addAll(result.roundHistory)

        _totalScore.value = result.totalScore
        _totalDarts.value = result.totalDarts
    }

    val hitHistory: List<androidx.compose.ui.geometry.Offset>
        get() = (_roundHistory.flatten() + _currentRoundHits).map { it.offset }

}