package com.example.dartsscore.engine

import com.example.dartsscore.DartHit

class CricketEngine : GameEngine {

    override fun addHit(
        hit: DartHit,
        currentRoundHits: List<DartHit>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.AddHitResult {

        return GameEngine.AddHitResult(
            roundHits = currentRoundHits,
            totalScore = totalScore,
            totalDarts = totalDarts,
            turnFinished = false
        )
    }

    override fun undoLastHit(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.UndoResult {

        return GameEngine.UndoResult(
            roundHits = currentRoundHits,
            roundHistory = roundHistory,
            totalScore = totalScore,
            totalDarts = totalDarts
        )
    }

    override fun finishTurn(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>
    ): GameEngine.FinishTurnResult {

        return GameEngine.FinishTurnResult(
            roundHits = emptyList(),
            roundHistory = roundHistory,
            turnFinished = true
        )
    }
}