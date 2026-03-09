package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dartsscore.viewmodel.PlayerViewModel
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.example.dartsscore.data.entity.Player

@Composable
fun PlayerManagementScreen() {

    val context = LocalContext.current
    val playerViewModel = remember { PlayerViewModel(context) }

    val players by playerViewModel.players.collectAsState()

    var name by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var deletedPlayer by remember { mutableStateOf<Player?>(null) }

    LaunchedEffect(Unit) {
        playerViewModel.loadPlayers()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                "Player Management",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {

                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Player Name") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            playerViewModel.addPlayer(name)
                            name = ""
                        }
                    }
                ) {
                    Text("Add")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {

                items(players) { player ->

                    PlayerRow(
                        player = player,
                        viewModel = playerViewModel,
                        onDeleteConfirmed = { deleted ->

                            coroutineScope.launch {

                                deletedPlayer = deleted

                                playerViewModel.deletePlayer(deleted)

                                val result = snackbarHostState.showSnackbar(
                                    message = "${deleted.name} deleted",
                                    actionLabel = "戻す",
                                    duration = SnackbarDuration.Long
                                )

                                if (result == SnackbarResult.ActionPerformed) {

                                    deletedPlayer?.let {
                                        playerViewModel.restorePlayer(it)
                                    }

                                }

                                deletedPlayer = null
                            }
                        }
                    )

                    Divider()

                }
            }
        }
    }
}

@Composable
fun PlayerRow(
    player: Player,
    viewModel: PlayerViewModel,
    onDeleteConfirmed: (Player) -> Unit
) {

    var average by remember { mutableStateOf<Double?>(null) }
    var throwCount by remember { mutableStateOf(0) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(player.id) {
        average = viewModel.getPlayerAverage(player.id)
        throwCount = viewModel.getThrowCount(player.id)
    }

    fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = player.name,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = average?.let { String.format("%.1f", it) } ?: "--",
            modifier = Modifier.weight(1f)
        )

        Text(
            text = throwCount.toString(),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = formatTime(player.createdAt),
            modifier = Modifier.weight(2f)
        )

        IconButton(
            onClick = { showDialog = true }
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }

    if (showDialog) {

        AlertDialog(

            onDismissRequest = { showDialog = false },

            title = {
                Text("Delete Player")
            },

            text = {
                Text("${player.name} を削除しますか？")
            },

            confirmButton = {

                Button(
                    onClick = {
                        onDeleteConfirmed(player)
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },

            dismissButton = {

                OutlinedButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}