package com.example.dartsscore

class CountUpEngine {
/*
    private var state = GameState()

    fun getState(): GameState {
        return state
    }

    fun startGame() {
        state = GameState()
    }

    fun addHit(hit: DartHit) {

        if (state.isFinished) return

        val newHits = state.dartsInRound + hit

        val newScore = state.currentScore + hit.score

        if (newHits.size < 3) {

            state = state.copy(
                currentScore = newScore,
                dartsInRound = newHits
            )

            return
        }

        // 3投終了

        val newHistory = state.roundHistory + listOf(newHits)

        val nextRound = state.currentRound + 1

        val finished = nextRound > state.maxRounds

        state = state.copy(
            currentScore = newScore,
            currentRound = nextRound,
            dartsInRound = emptyList(),
            roundHistory = newHistory,
            isFinished = finished
        )
    }

    fun undo() {

        if (state.dartsInRound.isNotEmpty()) {

            val last = state.dartsInRound.last()

            state = state.copy(
                currentScore = state.currentScore - last.score,
                dartsInRound = state.dartsInRound.dropLast(1)
            )

            return
        }

        if (state.roundHistory.isNotEmpty()) {

            val lastRound = state.roundHistory.last()

            val lastHit = lastRound.last()

            state = state.copy(
                currentScore = state.currentScore - lastHit.score,
                currentRound = state.currentRound - 1,
                dartsInRound = lastRound.dropLast(1),
                roundHistory = state.roundHistory.dropLast(1),
                isFinished = false
            )
        }
    }*/
}