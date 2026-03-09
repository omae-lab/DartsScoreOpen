package com.example.dartsscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dartsscore.data.entity.GameType
import com.example.dartsscore.ui.theme.DartsScoreTheme
import ui.GameScreen
import ui.GameSettingScreen
import ui.HomeScreen
import ui.PlayerManagementScreen
import ui.PlayerStatsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DartsScoreTheme {
                DartsApp()
            }
        }
    }
}

@Composable
fun DartsApp() {

    val navController = rememberNavController()
    val dartViewModel: DartViewModel = viewModel()

    Scaffold { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {

            composable("home") {
                HomeScreen(
                    onCountUpInfinite = { navController.navigate("gameSetting/${GameType.INFINITE_COUNT_UP}") },
                    onCountUp = { navController.navigate("gameSetting/${GameType.COUNT_UP}") },
                    onZeroOne = { navController.navigate("gameSetting/${GameType.ZERO_ONE_301}") },
                    onCricket = { navController.navigate("gameSetting/${GameType.CRICKET}") },
                    onOther = { },
                    onHistory = { },
                    onPlayer = { navController.navigate("playerManagement") }
                )
            }

            composable("gameSetting/{gameType}") { backStackEntry ->

                val typeString = backStackEntry.arguments?.getString("gameType")

                val gameType = try {
                    GameType.valueOf(typeString ?: "INFINITE_COUNT_UP")
                } catch (e: Exception) {
                    GameType.INFINITE_COUNT_UP
                }

                GameSettingScreen(
                    navController = navController,
                    gameType = gameType,
                    viewModel = dartViewModel
                )
            }

            composable("gameScreen/{gameType}") { backStackEntry ->

                val typeString = backStackEntry.arguments?.getString("gameType")

                val gameType = try {
                    GameType.valueOf(typeString ?: "INFINITE_COUNT_UP")
                } catch (e: Exception) {
                    GameType.INFINITE_COUNT_UP
                }

                GameScreen(
                    gameId = gameType.ordinal.toLong(),
                    viewModel = dartViewModel
                )
            }

            composable("playerManagement") {
                PlayerManagementScreen(navController)
            }

            composable("playerStats/{playerId}/{playerName}") { backStackEntry ->

                val playerId =
                    backStackEntry.arguments?.getString("playerId")!!.toLong()

                val playerName =
                    backStackEntry.arguments?.getString("playerName")!!

                PlayerStatsScreen(
                    playerId = playerId,
                    playerName = playerName
                )
            }
        }
    }
}