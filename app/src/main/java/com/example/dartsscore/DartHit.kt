package com.example.dartsscore

import androidx.compose.ui.geometry.Offset

data class DartHit(

    val number: Int,

    val multiplier: Int,

    val offset: Offset
) {
    val score: Int
        get() = number * multiplier

    val isDouble: Boolean
        get() = multiplier == 2

    val isTriple: Boolean
        get() = multiplier == 3

    val isBull: Boolean
        get() = number == 25
}