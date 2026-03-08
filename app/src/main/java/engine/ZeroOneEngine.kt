package com.example.dartsscore.engine

import com.example.dartsscore.DartHit
import com.example.dartsscore.data.entity.*

class ZeroOneEngine(
    private val startScore: Int,
    private val rules: ZeroOneRules = ZeroOneRules()
) : GameEngine {

    override val initialScore: Int = startScore

    override fun addHit(
        hit: DartHit,
        currentRoundHits: List<DartHit>,
        totalScore: Int,
        totalDarts: Int
    ): GameEngine.AddHitResult {

        val newRoundHits = currentRoundHits.toMutableList()
        newRoundHits.add(hit)

        val hitScore = calculateScore(hit)
        val remaining = totalScore - hitScore

        // Bust
        if (remaining < 0 || isInvalidFinish(remaining, hit)) {
            val roundStartScore = totalScore + currentRoundHits.sumOf { calculateScore(it) }
            return GameEngine.AddHitResult(
                roundHits = newRoundHits,
                totalScore = roundStartScore,
                totalDarts = totalDarts,
                turnFinished = true
            )
        }

        val newTotalScore = remaining
        val newTotalDarts = totalDarts + 1
        val finished = remaining == 0
        val turnFinished = finished || newRoundHits.size == 3

        return GameEngine.AddHitResult(
            roundHits = newRoundHits,
            totalScore = newTotalScore,
            totalDarts = newTotalDarts,
            turnFinished = turnFinished
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
            newTotalScore += calculateScore(last)
            newTotalDarts -= 1
            return GameEngine.UndoResult(newRoundHits, newRoundHistory, newTotalScore, newTotalDarts)
        }

        if (newRoundHistory.isNotEmpty()) {
            val lastRound = newRoundHistory.removeLast()
            val restoredHits = lastRound.toMutableList()
            val lastHit = restoredHits.removeLast()
            newRoundHits.addAll(restoredHits)
            newTotalScore += calculateScore(lastHit)
            newTotalDarts -= 1
        }

        return GameEngine.UndoResult(newRoundHits, newRoundHistory, newTotalScore, newTotalDarts)
    }

    override fun finishTurn(
        currentRoundHits: List<DartHit>,
        roundHistory: List<List<DartHit>>
    ): GameEngine.FinishTurnResult {
        if (currentRoundHits.isEmpty()) {
            return GameEngine.FinishTurnResult(currentRoundHits, roundHistory, false)
        }
        val newHistory = roundHistory.toMutableList()
        newHistory.add(currentRoundHits.toList())
        return GameEngine.FinishTurnResult(emptyList(), newHistory, false)
    }

    // -------------------------
    // Bullルール対応
    // -------------------------
    private fun calculateScore(hit: DartHit): Int {
        return if (hit.number == 25) {
            when (rules.bullRule) {
                BullRule.SINGLE_BULL_25 -> hit.score
                BullRule.DOUBLE_BULL_50 -> 50
            }
        } else {
            hit.score
        }
    }

    // -------------------------
    // OutRule判定
    // -------------------------
    private fun isInvalidFinish(remaining: Int, hit: DartHit): Boolean {
        if (remaining > 0) return false
        if (remaining < 0) return true

        return when (rules.outRule) {
            OutRule.OPEN -> false
            OutRule.DOUBLE -> hit.multiplier != 2
            OutRule.MASTER -> hit.multiplier < 2
        }
    }
}