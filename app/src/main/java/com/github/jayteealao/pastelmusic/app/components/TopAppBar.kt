package com.github.jayteealao.pastelmusic.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelTheme
import com.github.jayteealao.pastelmusic.app.ui.theme.fontFamily


@Composable
fun AppBar() {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        val assetsManager = LocalContext.current.assets
        val images = assetsManager.list("")?.filter { it.endsWith("jpg") }
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(color = PastelTheme.colors.pastelLightBlue)
                .size(36.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                .padding(8.dp),
            onClick = { /*TODO*/ }
        ) {
            Column() {
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = PastelTheme.colors.pastelYellow)
                            .size(10.dp)
                            .border(1.dp, Color.Black, CircleShape)
                    ) {

                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = PastelTheme.colors.pastelYellow)
                            .size(10.dp)
                            .border(1.dp, Color.Black, CircleShape)
                    ) {

                    }
                }
                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = PastelTheme.colors.pastelYellow)
                            .size(10.dp)
                            .border(1.dp, Color.Black, CircleShape)
                    ) {

                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = PastelTheme.colors.pastelYellow)
                            .size(10.dp)
                            .border(1.dp, Color.Black, CircleShape)
                    ) {

                    }
                }
            }
        }
        Text(
            modifier = Modifier.weight(2f),
            text = "Home",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontFamily = fontFamily,
            style = LocalTextStyle.current.copy(fontSize = 20.sp)
        )
        IconButton(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .size(36.dp)
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .padding(1.dp),
            onClick = { /*TODO*/ }
        ) {
            AsyncImage(
                model = images?.random()?.let { assetsManager.open(it).readBytes() },
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(36.dp)
            )
        }
    }

}

@Preview
@Composable
fun PreviewAppBar() {
    AppBar()
}