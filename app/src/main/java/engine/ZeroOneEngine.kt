package com.example.dartsscore.engine

import com.example.dartsscore.DartHit

class ZeroOneEngine(
    private val startScore: Int
) : GameEngine {

    override fun addHit(
        hit: DartHit,
        currentRoundHits: List<DartHit>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.AddHitResult {

        val newRoundHits = currentRoundHits + hit

        return GameEngine.AddHitResult(
            roundHits = newRoundHits,
            totalScore = totalScore,
            totalDarts = totalDarts + 1,
            turnFinished = newRoundHits.size >= 3
        )
    }

    override fun undoLastHit(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.UndoResult {

        val newHits =
            if (currentRoundHits.isNotEmpty())
                currentRoundHits.dropLast(1)
            else
                currentRoundHits

        return GameEngine.UndoResult(
            roundHits = newHits,
            roundHistory = roundHistory,
            totalScore = totalScore,
            totalDarts = if (totalDarts > 0) totalDarts - 1 else 0
        )
    }

    override fun finishTurn(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>
    ): GameEngine.FinishTurnResult {

        val newHistory =
            if (currentRoundHits.isNotEmpty())
                roundHistory + listOf(currentRoundHits)
            else
                roundHistory

        return GameEngine.FinishTurnResult(
            roundHits = emptyList(),
            roundHistory = newHistory,
            turnFinished = false
        )
    }
}