package engine
import com.example.dartsscore.DartHit

data class GameState(
    val totalScore: Int,
    val round: Int,
    val dartsInRound: Int,
    val roundStartScore: Int,
    val roundHistory: List<DartHit>,
    val isGameFinished: Boolean
)