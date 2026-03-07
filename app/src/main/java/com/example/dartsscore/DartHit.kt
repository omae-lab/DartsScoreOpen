package com.example.dartsscore

import androidx.compose.ui.geometry.Offset

data class DartHit(

    val number: Int,

    val multiplier: Int,

    val offset: Offset
) {
    val score: Int
        get() = number * multiplier
}