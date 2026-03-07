package engine

import com.example.dartsscore.DartHit

class CountUpEngine(private val maxRounds: Int = 8) {

    private var currentRound = 1
    private val rounds = mutableListOf<MutableList<DartHit>>()
    var totalScore = 0
        private set

    val isFinished: Boolean
        get() = currentRound > maxRounds

    fun addHit(hit: DartHit) {
        totalScore += hit.score
        if (rounds.size < currentRound) {
            rounds.add(mutableListOf(hit))
        } else {
            rounds[currentRound - 1].add(hit)
        }
        if (rounds[currentRound - 1].size == 3) currentRound++
    }

    fun undoLastHit(): DartHit? {
        if (rounds.isEmpty()) return null
        val lastRound = rounds.last()
        val removed = lastRound.removeLast()
        totalScore -= removed.score
        if (lastRound.isEmpty()) rounds.removeLast()
        if (currentRound > 1 && lastRound.isEmpty()) currentRound--
        return removed
    }

    fun getRoundHits(): List<List<DartHit>> = rounds
}