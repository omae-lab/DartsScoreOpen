package com.example.dartsscore

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.*

private val SEGMENTS = listOf(
    20, 1, 18, 4, 13, 6, 10, 15, 2, 17,
    3, 19, 7, 16, 8, 11, 14, 9, 12, 5
)

data class BoardRadii(
    val tripleInner: Float,
    val tripleOuter: Float,
    val doubleInner: Float,
    val doubleOuter: Float,
    val outerBull: Float,
    val innerBull: Float
)

@Composable
fun DartBoard(
    modifier: Modifier = Modifier,
    onHit: (DartHit) -> Unit
) {

    var boardRadius by remember { mutableStateOf(0f) }
    var boardCenter by remember { mutableStateOf(Offset.Zero) }
    var boardRadii by remember { mutableStateOf<BoardRadii?>(null) }

    Canvas(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .pointerInput(boardRadius, boardCenter, boardRadii) {
                detectTapGestures { offset ->
                    val radii = boardRadii ?: return@detectTapGestures
                    val hit = calculateScore(
                        offset,
                        boardCenter,
                        boardRadius,
                        radii
                    )
                    hit?.let { onHit(it) }
                }
            }
    ) {

        boardRadius = size.minDimension / 2f
        boardCenter = center
        boardRadii = createRadii(boardRadius)

        val radii = boardRadii!!

        val segmentAngle = 360f / 20f
        val halfSegment = segmentAngle / 2f
        val startOffset = -90f - halfSegment

        val doubleCenter = (radii.doubleInner + radii.doubleOuter) / 2f
        val doubleThickness = radii.doubleOuter - radii.doubleInner

        val tripleCenter = (radii.tripleInner + radii.tripleOuter) / 2f
        val tripleThickness = radii.tripleOuter - radii.tripleInner

        SEGMENTS.forEachIndexed { index, number ->

            val startAngle = startOffset + index * segmentAngle
            val baseColor =
                if (index % 2 == 0) Color(0xFF111111) else Color(0xFFEEEEEE)
            val ringColor =
                if (index % 2 == 0) Color.Red else Color.Blue

            drawArc(
                color = baseColor,
                startAngle = startAngle,
                sweepAngle = segmentAngle,
                useCenter = true,
                size = Size(radii.doubleInner * 2, radii.doubleInner * 2),
                topLeft = Offset(
                    boardCenter.x - radii.doubleInner,
                    boardCenter.y - radii.doubleInner
                )
            )

            drawArc(
                color = ringColor,
                startAngle = startAngle,
                sweepAngle = segmentAngle,
                useCenter = false,
                style = Stroke(width = tripleThickness),
                size = Size(tripleCenter * 2, tripleCenter * 2),
                topLeft = Offset(
                    boardCenter.x - tripleCenter,
                    boardCenter.y - tripleCenter
                )
            )

            drawArc(
                color = ringColor,
                startAngle = startAngle,
                sweepAngle = segmentAngle,
                useCenter = false,
                style = Stroke(width = doubleThickness),
                size = Size(doubleCenter * 2, doubleCenter * 2),
                topLeft = Offset(
                    boardCenter.x - doubleCenter,
                    boardCenter.y - doubleCenter
                )
            )
        }

        val textRadius = (radii.tripleOuter + radii.doubleInner) / 2f

        val textPaint = Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = boardRadius * 0.08f
            isAntiAlias = true
            isFakeBoldText = true
        }

        SEGMENTS.forEachIndexed { index, number ->

            val angleDeg = startOffset + index * segmentAngle + halfSegment
            val angleRad = Math.toRadians(angleDeg.toDouble())

            val x = boardCenter.x + textRadius * cos(angleRad).toFloat()
            val y = boardCenter.y + textRadius * sin(angleRad).toFloat()

            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    number.toString(),
                    x,
                    y + textPaint.textSize / 3,
                    textPaint
                )
            }
        }

        drawCircle(Color.Red, radius = radii.outerBull, center = boardCenter)
        drawCircle(Color.Black, radius = radii.innerBull, center = boardCenter)
    }
}

private fun createRadii(boardRadius: Float) = BoardRadii(
    tripleInner = boardRadius * 0.40f,
    tripleOuter = boardRadius * 0.55f,
    doubleInner = boardRadius * 0.80f,
    doubleOuter = boardRadius * 0.95f,
    outerBull = boardRadius * 0.15f,
    innerBull = boardRadius * 0.07f
)

fun calculateScore(
    offset: Offset,
    center: Offset,
    boardRadius: Float,
    radii: BoardRadii
): DartHit? {

    val dx = offset.x - center.x
    val dy = offset.y - center.y
    val distance = sqrt(dx * dx + dy * dy)

    if (distance > boardRadius) return null
    if (distance <= radii.innerBull) return DartHit(25, 2)
    if (distance <= radii.outerBull) return DartHit(25, 1)

    var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
    angle += 90f
    if (angle < 0f) angle += 360f

    val segmentAngle = 360f / 20f
    val halfSegment = segmentAngle / 2f

    val index = ((angle + halfSegment) / segmentAngle).toInt() % 20
    val number = SEGMENTS[index]

    return when {
        distance in radii.tripleInner..radii.tripleOuter ->
            DartHit(number, 3)
        distance in radii.doubleInner..radii.doubleOuter ->
            DartHit(number, 2)
        else ->
            DartHit(number, 1)
    }
}