package com.github.jayteealao.pastelmusic.app.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

/**
 * Custom artwork component for displaying album/artist images.
 * Handles loading states and fallback placeholder.
 *
 * @param artworkUri URI of the artwork to display
 * @param contentDescription Accessibility description
 * @param modifier Modifier for sizing and styling
 * @param contentScale How to scale the image
 */
@Composable
fun TagArtwork(
    artworkUri: Uri?,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(artworkUri)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading,
            is AsyncImagePainter.State.Error,
            is AsyncImagePainter.State.Empty -> {
                // Placeholder when image is loading or failed
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF5F5F5)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = "Music placeholder",
                        tint = Color(0xFFBDBDBD),
                        modifier = Modifier.fillMaxSize(0.5f)
                    )
                }
            }
            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = contentScale,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
