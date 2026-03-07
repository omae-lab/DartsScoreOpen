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
import ui.PrecisionAimOverlay
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
    hitHistory: List<Offset>,
    onHit: (DartHit) -> Unit
) {

    var boardRadius by remember { mutableStateOf(0f) }
    var boardCenter by remember { mutableStateOf(Offset.Zero) }
    var boardRadii by remember { mutableStateOf<BoardRadii?>(null) }

    var showPrecision by remember { mutableStateOf(false) }
    var precisionStart by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .pointerInput(boardRadius, boardCenter, boardRadii) {

                detectTapGestures(

                    onTap = { offset ->

                        val radii = boardRadii ?: return@detectTapGestures

                        val hit = calculateScore(
                            offset,
                            boardCenter,
                            boardRadius,
                            radii
                        )

                        // 修正: DartHit を生成して1つ渡す
                        hit?.let { dartHit ->
                            onHit(DartHit(dartHit.number, dartHit.multiplier, offset))
                        }
                    },

                    onLongPress = { offset ->

                        precisionStart = offset
                        showPrecision = true
                    }
                )
            }
    ) {

        boardRadius = size.minDimension / 2f
        boardCenter = center
        boardRadii = createSoftTipRadii(boardRadius)

        val radii = boardRadii!!

        drawDartBoard(boardCenter, boardRadius, radii)
        drawNumbers(boardCenter, boardRadius, radii)
        drawHeatMap(hitHistory, boardRadius)
    }

    if (showPrecision && boardRadii != null) {

        PrecisionAimOverlay(

            startOffset = precisionStart,
            boardCenter = boardCenter,
            boardRadius = boardRadius,
            radii = boardRadii!!,

            onConfirm = { hitOffset ->

                // 修正: offset ではなく hitOffset を使う
                val hit = calculateScore(
                    hitOffset,
                    boardCenter,
                    boardRadius,
                    boardRadii!!
                )

                hit?.let {
                    val dartHit = DartHit(
                        number = it.number,
                        multiplier = it.multiplier,
                        offset = hitOffset
                    )
                    onHit(dartHit)
                }

                showPrecision = false
            },

            onCancel = {
                showPrecision = false
            }
        )
    }
}

private fun createSoftTipRadii(boardRadius: Float) = BoardRadii(

    innerBull = boardRadius * 0.07f,
    outerBull = boardRadius * 0.16f,

    tripleInner = boardRadius * 0.40f,
    tripleOuter = boardRadius * 0.55f,

    doubleInner = boardRadius * 0.80f,
    doubleOuter = boardRadius * 0.95f
)

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawHeatMap(
    hits: List<Offset>,
    boardRadius: Float
) {
    hits.forEach {
        drawCircle(
            color = Color.Yellow.copy(alpha = 0.6f),
            radius = boardRadius * 0.015f,
            center = it
        )
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawNumbers(
    boardCenter: Offset,
    boardRadius: Float,
    radii: BoardRadii
) {

    val segmentAngle = 360f / 20f
    val halfSegment = segmentAngle / 2f
    val startOffset = -90f - halfSegment

    val textRadius = radii.doubleOuter + boardRadius * 0.05f

    val textPaint = Paint().apply {

        color = android.graphics.Color.WHITE
        textAlign = Paint.Align.CENTER
        textSize = boardRadius * 0.08f
        isFakeBoldText = true
        isAntiAlias = true
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
}

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
    if (distance <= radii.innerBull) return DartHit(25, 2, offset)
    if (distance <= radii.outerBull) return DartHit(25, 1, offset)

    var angle = Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()

    angle += 90f
    if (angle < 0f) angle += 360f

    val segmentAngle = 360f / 20f
    val halfSegment = segmentAngle / 2f

    val index = ((angle + halfSegment) / segmentAngle).toInt() % 20
    val number = SEGMENTS[index]

    return when {
        distance in radii.tripleInner..radii.tripleOuter ->
            DartHit(number, 3, offset)
        distance in radii.doubleInner..radii.doubleOuter ->
            DartHit(number, 2, offset)
        else ->
            DartHit(number, 1, offset)
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawDartBoard(
    boardCenter: Offset,
    boardRadius: Float,
    radii: BoardRadii
) {

    val segmentAngle = 360f / 20f
    val halfSegment = segmentAngle / 2f
    val startOffset = -90f - halfSegment

    val doubleCenter = (radii.doubleInner + radii.doubleOuter) / 2f
    val doubleThickness = radii.doubleOuter - radii.doubleInner

    val tripleCenter = (radii.tripleInner + radii.tripleOuter) / 2f
    val tripleThickness = radii.tripleOuter - radii.tripleInner

    SEGMENTS.forEachIndexed { index, _ ->

        val startAngle = startOffset + index * segmentAngle

        val baseColor =
            if (index % 2 == 0) Color(0xFF111111)
            else Color(0xFFEEEEEE)

        val ringColor =
            if (index % 2 == 0) Color.Red
            else Color.Blue

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

    drawCircle(Color.Red, radii.outerBull, boardCenter)
    drawCircle(Color.Black, radii.innerBull, boardCenter)
}