package com.github.jayteealao.pastelmusic.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun PastelCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    PastelLayout {
        content()
    }
}

enum class PastelParts {
    FRONT,
    BACK
}

@Composable
fun PastelLayout(
    modifier: Modifier = Modifier,
    offset: Dp = 6.dp,
    color: Color = Color.Cyan,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit,
) {
    Layout(
        content = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer {
                        scaleX = 0.94f
                    }
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = borderWidth,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(color)
                    .layoutId(PastelParts.BACK)
            )
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .border(
                        width = borderWidth,
                        color = Color.Black,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .layoutId(PastelParts.FRONT)
            ) {
                content()
            }
        },
        modifier = modifier
    ) { measurables, constraints ->

        val offset = offset.roundToPx()

        val frontPlaceable = measurables.first { measurable ->
            measurable.layoutId == PastelParts.FRONT
        }.measure(constraints)

        val backPlaceable = measurables.first { measurable ->
            measurable.layoutId == PastelParts.BACK
        }.measure(Constraints.fixed(frontPlaceable.width, frontPlaceable.height))

        layout(frontPlaceable.width, frontPlaceable.height + offset) {
            backPlaceable.place(0, offset, 0f)
            frontPlaceable.place(0, 0, 1f)
        }
    }
}

fun Modifier.addPastelShadow() = this.then(
//    scale(0.95f)
        drawWithContent {
            inset(horizontal = 0f, vertical = -10f) {

        drawRoundRect(
            color = Color.Cyan,
            size = size,
            cornerRadius = CornerRadius(x = 16.dp.toPx(), 16.dp.toPx()),
            topLeft = Offset(x = 0f, y = 0.01f * size.height),
            style = Fill
        )
            }
    }
)

@Preview
@Composable
fun PreviewModifier() {
    Box(modifier = Modifier.wrapContentSize().addPastelShadow()) {
        Box(modifier = Modifier.size(200.dp).background(Color.White))
    }
}

@Preview
@Composable
fun PrevPastelCard() {
    PastelCard() {
        Box(modifier = Modifier.size(200.dp))
    }
}