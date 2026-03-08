package com.example.dartsscore.engine

import com.example.dartsscore.DartHit

interface GameEngine {

    val initialScore: Int

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

    fun addHit(
        hit: DartHit,
        currentRoundHits: List<DartHit>,
        totalScore: Int,
        totalDarts: Int
    ): AddHitResult

    fun undoLastHit(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>,
        totalScore: Int,
        totalDarts: Int
    ): UndoResult

    fun finishTurn(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>
    ): FinishTurnResult
}