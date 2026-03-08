package com.example.dartsscore.engine

import com.example.dartsscore.DartHit

class CountUpEngine(
    private val maxRounds: Int? = 8
) : GameEngine {
    override fun addHit(
        hit: DartHit,
        currentRoundHits: List<DartHit>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.AddHitResult {

        val newRoundHits = currentRoundHits.toMutableList()
        newRoundHits.add(hit)

        val newTotalScore = totalScore + hit.score
        val newTotalDarts = totalDarts + 1

        val turnFinished = newRoundHits.size == 3

        return GameEngine.AddHitResult(
            newRoundHits,
            newTotalScore,
            newTotalDarts,
            turnFinished
        )
    }

    override fun undoLastHit(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.UndoResult {

        val newRoundHits = currentRoundHits.toMutableList()
        val newRoundHistory = roundHistory.toMutableList()

        var newTotalScore = totalScore
        var newTotalDarts = totalDarts

        if (newRoundHits.isNotEmpty()) {

            val last = newRoundHits.removeLast()

            newTotalScore -= last.score
            newTotalDarts -= 1

            return GameEngine.UndoResult(
                newRoundHits,
                newRoundHistory,
                newTotalScore,
                newTotalDarts
            )
        }

        if (newRoundHistory.isNotEmpty()) {

            val lastRound = newRoundHistory.removeLast()

            newRoundHits.addAll(lastRound)

            val lastHit = newRoundHits.removeLast()

            newTotalScore -= lastHit.score
            newTotalDarts -= 1
        }

        return GameEngine.UndoResult(
            newRoundHits,
            newRoundHistory,
            newTotalScore,
            newTotalDarts
        )
    }

    override fun finishTurn(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>
    ): GameEngine.FinishTurnResult {

        if (currentRoundHits.isEmpty()) {
            return GameEngine.FinishTurnResult(
                currentRoundHits,
                roundHistory,
                false
            )
        }

        val newHistory = roundHistory.toMutableList()
        newHistory.add(currentRoundHits.toList())

        return GameEngine.FinishTurnResult(
            emptyList(),
            newHistory,
            false
        )
    }

    data class AddHitResult(
        val roundHits: List<DartHit>,
        val totalScore: Int,
        val totalDarts: Int,
        val turnFinished: Boolean
    )

    data class UndoResult(
        val roundHits: List<DartHit>,
        val roundHistory: List<List<DartHit>>,
        val totalScore: Int,
        val totalDarts: Int
    )

    data class FinishTurnResult(
        val roundHits: List<DartHit>,
        val roundHistory: List<List<DartHit>>,
        val turnFinished: Boolean
    )
}