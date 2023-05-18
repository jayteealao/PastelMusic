package com.github.jayteealao.pastelmusic.app.components

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope
import com.github.jayteealao.pastelmusic.app.ui.icons.PastelIcons

@Composable
fun PastelImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
//    colors: Color = Color.White,
    loading: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit)? = null,
    error: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    Card(modifier = modifier, shape = shape, elevation = 0.dp) {
        SubcomposeAsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = model,
            contentDescription = contentDescription,
            loading = loading,
            error = error,
            contentScale = contentScale
        )
    }
}

@Composable
fun PastelArtworkImage(
    artworkUri: Uri,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    placeholder: @Composable () -> Unit = {
        Card(
            shape = shape,

        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = PastelIcons.Music.resourceId),
                contentDescription = contentDescription
            )
        }
    },
    contentScale: ContentScale = ContentScale.Fit
) {
    PastelImage(
        modifier = modifier,
        model = artworkUri,
        contentDescription = contentDescription,
        shape = shape,
        loading = { placeholder() },
        error = { placeholder() },
        contentScale = contentScale
    )
}