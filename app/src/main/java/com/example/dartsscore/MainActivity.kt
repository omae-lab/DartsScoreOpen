package com.example.dartsscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dartsscore.ui.theme.DartsScoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DartsScoreTheme {
                val dartViewModel: DartViewModel = viewModel()
                Scaffold { innerPadding: PaddingValues ->
                    CountUpScreen(
                        viewModel = dartViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CountUpScreen(
    viewModel: DartViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DartBoard(
            hitHistory = viewModel.hitHistory,
            onHit = { hit ->
                viewModel.addHit(hit)
            }
        )

        Button(
            onClick = { viewModel.undoLastHit() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("UNDO", color = Color.White)
        }

        // 修正: currentScore -> totalScore
        Text(
            text = viewModel.totalScore.value.toString(),
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "AVG ${"%.2f".format(viewModel.average)}",
            fontSize = 28.sp,
            color = Color.Cyan,
            modifier = Modifier.padding(top = 4.dp)
        )

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

        if (viewModel.turnFinished.value) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = { viewModel.finishTurn() }) { Text("Next Round") }
                Button(onClick = { viewModel.finishTurn() }) { Text("Next Player") }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
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