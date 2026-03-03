package com.example.dartsscore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dartsscore.ui.theme.DartsScoreTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

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

data class DartHit(
    val number: Int,
    val multiplier: Int
) {
    val score: Int
        get() = number * multiplier
}

@Composable
fun CountUpScreen(
    viewModel: DartViewModel,
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //Text(text = "Score: ${viewModel.currentScore.value}")

        /*Text(
            text = "Current Round: ${
                viewModel.currentRoundHits.sumOf { it.score }
            }"
        )*/

        DartBoard(
            onHit = { hit ->
                viewModel.addHit(hit)
            }
        )

        Button(
            onClick = { viewModel.undoLastHit() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Text("UNDO", color = Color.White)
        }

        Text(
            text = viewModel.currentScore.value.toString(),
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Green,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "History",
            fontSize = 16.sp,
            color = Color.LightGray)

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
