package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dartsscore.DartViewModel
import com.example.dartsscore.data.entity.*
import com.example.dartsscore.data.entity.ZeroOneRules

data class GameSettings(
    val gameType: GameType,
    val initialScore: Int = 0,
    val inRule: InRule? = null,
    val outRule: OutRule? = null,
    val bullRule: BullRule? = null
)

@Composable
fun GameSettingScreen(
    navController: NavController,
    gameType: GameType,
    viewModel: DartViewModel
) {
    viewModel.startGame(gameType)

    var initialScore by remember { mutableStateOf(501) }
    var inRule by remember { mutableStateOf(InRule.OPEN) }
    var outRule by remember { mutableStateOf(OutRule.DOUBLE) }
    var bullRule by remember { mutableStateOf(BullRule.SINGLE_BULL_25) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = when (gameType) {
                GameType.INFINITE_COUNT_UP -> "∞ Count Up"
                GameType.COUNT_UP -> "Count Up"
                GameType.ZERO_ONE_301 -> "01"
                GameType.CRICKET -> "Cricket"
                else -> "Other"
            },
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Player Settings (仮)",
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (gameType == GameType.ZERO_ONE_301) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                Text("Initial Score", color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(301, 501, 701).forEach { score ->
                        Button(
                            onClick = { initialScore = score },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (initialScore == score) Color.Green else Color.DarkGray
                            )
                        ) { Text("$score", color = Color.White) }
                    }
                }

                Text("In Rule", color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InRule.values().forEach { rule ->
                        Button(
                            onClick = { inRule = rule },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (inRule == rule) Color.Green else Color.DarkGray
                            )
                        ) { Text(rule.name, color = Color.White) }
                    }
                }

                Text("Out Rule", color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutRule.values().forEach { rule ->
                        Button(
                            onClick = { outRule = rule },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (outRule == rule) Color.Green else Color.DarkGray
                            )
                        ) { Text(rule.name, color = Color.White) }
                    }
                }

                Text("Bull Rule", color = Color.LightGray)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BullRule.values().forEach { rule ->
                        Button(
                            onClick = { bullRule = rule },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (bullRule == rule) Color.Green else Color.DarkGray
                            )
                        ) { Text(rule.name, color = Color.White) }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = {
                val settings = if (gameType == GameType.ZERO_ONE_301) {
                    GameSettings(
                        gameType = gameType,
                        initialScore = initialScore,
                        inRule = inRule,
                        outRule = outRule,
                        bullRule = bullRule
                    )
                } else {
                    GameSettings(gameType = gameType)
                }
                viewModel.applySettings(settings)
                navController.navigate("gameScreen/${gameType.name}")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) { Text("Start Game", color = Color.Black) }
    }
}