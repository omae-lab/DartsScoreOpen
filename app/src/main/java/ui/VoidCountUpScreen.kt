package com.example.dartsscore
/*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue

@Composable
fun CountUpScreen(
    viewModel: DartViewModel,
    modifier: Modifier = Modifier
) {
    val throws: List<com.example.dartsscore.data.entity.Throw> by viewModel.throwsFlow.collectAsState(initial = emptyList())
    val listState = rememberLazyListState()

    Column(modifier = modifier.padding(16.dp)) {

        DartBoard(
            hitHistory = viewModel.hitHistory.map { it.offset },
            onHit = { hit -> viewModel.addHit(hit) }
        )

        Button(onClick = { viewModel.undoLastHit() }) {
            Text("UNDO")
        }

        /*Text(
            text = "Total: ${viewModel.totalScore}",
            fontSize = 32.sp,
            color = Color.Green
        )

        Text(
            text = "AVG: ${"%.2f".format(viewModel.average())}",
            fontSize = 24.sp,
            color = Color.Cyan
        )*/

        Text("Score: ${viewModel.totalScore}")
        Text("AVG: ${"%.2f".format(viewModel.average)}")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Throw History", fontSize = 16.sp, color = Color.White)
        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(throws) { t ->
                Text(
                    text = "Segment: ${t.segment}, Multiplier: ${t.multiplier}, X:${t.hitX}, Y:${t.hitY}",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}*/