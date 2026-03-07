package engine
import com.example.dartsscore.DartHit

data class GameState(
    val currentScore: Int = 0,
    val currentRound: Int = 1,
    val maxRounds: Int = 8,
    val dartsInRound: List<DartHit> = emptyList(),
    val roundHistory: List<List<DartHit>> = emptyList(),
    val isFinished: Boolean = false
)