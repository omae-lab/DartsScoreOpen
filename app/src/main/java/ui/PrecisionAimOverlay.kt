package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import com.example.dartsscore.BoardRadii
import com.example.dartsscore.drawDartBoard

@Composable
fun PrecisionAimOverlay(
    startOffset: Offset,
    boardCenter: Offset,
    boardRadius: Float,
    radii: BoardRadii,
    onConfirm: (Offset) -> Unit,
    onCancel: () -> Unit
) {

    var boardOffset by remember { mutableStateOf(Offset.Zero) }

    val zoomScale = 3.5f

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {

                detectDragGestures(

                    onDrag = { change, dragAmount ->
                        boardOffset += dragAmount
                    },

                    onDragEnd = {

                        val hit = Offset(
                            startOffset.x - boardOffset.x / zoomScale,
                            startOffset.y - boardOffset.y / zoomScale
                        )

                        onConfirm(hit)
                    }
                )
            }
    ) {

        val cursor = startOffset

        // ズーム描画
        withTransform({

            translate(
                boardOffset.x,
                boardOffset.y
            )

            scale(
                zoomScale,
                zoomScale,
                pivot = startOffset
            )

        }) {

            drawDartBoard(
                boardCenter = boardCenter,
                boardRadius = boardRadius,
                radii = radii
            )
        }

        // カーソル描画
        val crossSize = 40f

        drawLine(
            color = Color.Yellow,
            start = Offset(cursor.x - crossSize, cursor.y),
            end = Offset(cursor.x + crossSize, cursor.y),
            strokeWidth = 4f
        )

        drawLine(
            color = Color.Yellow,
            start = Offset(cursor.x, cursor.y - crossSize),
            end = Offset(cursor.x, cursor.y + crossSize),
            strokeWidth = 4f
        )

        drawCircle(
            color = Color.Yellow,
            radius = 6f,
            center = cursor
        )

        drawCircle(
            color = Color.Green,
            radius = 10f,
            center = startOffset
        )
    }
}