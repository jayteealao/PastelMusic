package com.github.jayteealao.pastelmusic.app.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Custom hook component for tag-style cards.
 * Creates a filled hole punch effect with solid rings.
 *
 * @param modifier Modifier for sizing and positioning
 * @param strokeWidth Width of the hook's outline
 */
@Composable
fun TagHook(
    modifier: Modifier = Modifier,
    strokeWidth: Float = 2f
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val centerX = width / 2f

        // Outer filled white circle
        val outerRadius = width / 2f
        drawCircle(
            color = Color.White,
            radius = outerRadius,
            center = Offset(centerX, outerRadius),
            style = Fill
        )

        // Black stroke around outer circle
        drawCircle(
            color = Color.Black,
            radius = outerRadius,
            center = Offset(centerX, outerRadius),
            style = Stroke(width = strokeWidth)
        )

        // Inner filled black circle (the actual hole)
        val innerRadius = outerRadius * 0.6f
        drawCircle(
            color = Color.Black,
            radius = innerRadius,
            center = Offset(centerX, outerRadius),
            style = Fill
        )

        // Connector bar to card
        val connectorWidth = width * 0.25f
        val connectorHeight = height - outerRadius

        if (connectorHeight > 0) {
            // Black background for connector
            drawRect(
                color = Color.Black,
                topLeft = Offset(
                    (width - connectorWidth) / 2f - strokeWidth,
                    outerRadius
                ),
                size = Size(
                    connectorWidth + (strokeWidth * 2),
                    connectorHeight
                )
            )

            // White fill for connector
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(
                    (width - connectorWidth) / 2f,
                    outerRadius
                ),
                size = Size(
                    connectorWidth,
                    connectorHeight
                ),
                cornerRadius = CornerRadius(2f, 2f)
            )
        }
    }
}
