package com.example.dartsscore.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun DartsScoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = MaterialTheme.colorScheme.primary,
            secondary = MaterialTheme.colorScheme.secondary
        ),
        typography = Typography(),
        content = content
    )
}