package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.dartsscore.viewmodel.PlayerViewModel
import com.example.dartsscore.data.entity.Player

@Composable
fun PlayerStatsScreen(
    player: Player
) {

    val context = LocalContext.current
    val viewModel = remember { PlayerViewModel(context) }

    var average by remember { mutableStateOf<Double?>(null) }
    var throwCount by remember { mutableStateOf(0) }

    var t20Hits by remember { mutableStateOf(0) }
    var t19Hits by remember { mutableStateOf(0) }
    var bullHits by remember { mutableStateOf(0) }

    LaunchedEffect(player.id) {

        average = viewModel.getPlayerAverage(player.id)
        throwCount = viewModel.getThrowCount(player.id)

        t20Hits = viewModel.countHitsByBed(player.id, "T20")
        t19Hits = viewModel.countHitsByBed(player.id, "T19")
        bullHits = viewModel.countHitsByBed(player.id, "BULL")

    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        item {

            Text(
                "Player Stats",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                player.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Average : ${average?.let { String.format("%.1f", it) } ?: "--"}")

            Text("Total Throws : $throwCount")

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "BED Hits",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("T20 : $t20Hits")
            Text("T19 : $t19Hits")
            Text("Bull : $bullHits")

        }
    }
}