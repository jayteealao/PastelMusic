package com.github.jayteealao.pastelmusic.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Hook(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    Canvas(modifier = modifier) {
        val bottomCircleWidth = size.width / 2
        val topCircleWidth = bottomCircleWidth - bottomCircleWidth / 5
        val radius = CornerSize(50).toPx(Size(size.width/5, size.height - bottomCircleWidth * 2), density)
        drawCircle(color = Color.White,
            radius = bottomCircleWidth,
            center = Offset(bottomCircleWidth, bottomCircleWidth)
        )
        drawCircle(color = Color.Black,
            radius = topCircleWidth,
            center = Offset(bottomCircleWidth, bottomCircleWidth))
        drawCircle(color = Color.White,
            radius = bottomCircleWidth,
            center = Offset(bottomCircleWidth, size.height - bottomCircleWidth)
        )
        drawCircle(color = Color.Black,
            radius = topCircleWidth,
            center = Offset(bottomCircleWidth, size.height - bottomCircleWidth)
        )
        drawRoundRect(
            color = Color.Black,
            size = Size(size.width/5 + 1.dp.toPx(), size.height - bottomCircleWidth * 2),
            topLeft = Offset(x = center.x - size.width/10 - 1.dp.toPx() / 2, y = topCircleWidth + bottomCircleWidth/5)
        )
        drawRoundRect(
            color = Color.White,
            size = Size(size.width/5, size.height - bottomCircleWidth * 2),
            topLeft = Offset(x = center.x - size.width/10, y = topCircleWidth + bottomCircleWidth/5),
            cornerRadius = CornerRadius(radius, radius)
        )
    }
}

@Preview
@Composable
fun PreviewHook() {
    Hook(Modifier.height(48.dp).width(16.dp))
}