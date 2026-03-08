package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dartsscore.DartBoard
import com.example.dartsscore.DartViewModel
import com.example.dartsscore.DartHit
//import ui.DartBoard

@Composable
fun GameScreen(
    gameId: Long,
    viewModel: DartViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ダーツボード
        DartBoard(
            hitHistory = viewModel.hitHistory,
            onHit = { hit: DartHit -> viewModel.addHit(hit) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // UNDOボタン
        Button(
            onClick = { viewModel.undoLastHit() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("UNDO", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // トータルスコア
        Text(
            text = viewModel.totalScore.value.toString(),
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // 平均スコア
        Text(
            text = "AVG ${"%.2f".format(viewModel.average)}",
            fontSize = 28.sp,
            color = Color.Cyan,
            modifier = Modifier.padding(top = 4.dp)
        )

        // 現ラウンドの投げ
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(3) { index ->
                val hit = viewModel.currentRoundHits.getOrNull(index)
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.DarkGray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = hit?.score?.toString() ?: "-",
                        fontSize = 28.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ラウンド終了後ボタン
        if (viewModel.turnFinished.value) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { viewModel.finishTurn() }) { Text("Next Round") }
                Button(onClick = { viewModel.finishTurn() }) { Text("Next Player") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 投げ履歴
        Text(text = "History", fontSize = 16.sp, color = Color.LightGray)

        val listState = rememberLazyListState()
        LaunchedEffect(viewModel.roundHistory.size) {
            if (viewModel.roundHistory.isNotEmpty()) {
                listState.scrollToItem(viewModel.roundHistory.size - 1)
            }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(viewModel.roundHistory) { index, round ->
                val scores = round.map { it.score }
                val roundTotal = scores.sum()
                val scoreText = scores.joinToString(" / ")
                Text(
                    text = "Round ${index + 1}: $scoreText = $roundTotal",
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontSize = 16.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}