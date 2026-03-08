package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onCountUpInfinite: () -> Unit,
    onCountUp: () -> Unit,
    onZeroOne: () -> Unit,
    onCricket: () -> Unit,
    onOther: () -> Unit,
    onHistory: () -> Unit,
    onPlayer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // 1行目
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeCardButton(
                text = "∞Count Up",
                onClick = onCountUpInfinite,
                modifier = Modifier.weight(1f)
            )
            HomeCardButton(
                text = "Count Up",
                onClick = onCountUp,
                modifier = Modifier.weight(1f)
            )
        }

        // 2行目
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HomeCardButton(
                text = "01",
                onClick = onZeroOne,
                modifier = Modifier.weight(1f)
            )
            HomeCardButton(
                text = "Cricket",
                onClick = onCricket,
                modifier = Modifier.weight(1f)
            )
        }

        // 3行目
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeCardButton(
                text = "Other",
                onClick = onOther,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 4行目
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeCardButton(
                text = "History",
                onClick = onHistory,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 5行目
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            HomeCardButton(
                text = "Player",
                onClick = onPlayer,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun HomeCardButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .background(Color(0xFF1E1E1E), shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = Color.White)
    }
}