package com.github.jayteealao.pastelmusic.app.ui.preview

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jayteealao.pastelmusic.app.components.ArtistTagCard
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme

@Preview(name = "Artist Tag Card - Normal", showBackground = true)
@Composable
fun PreviewArtistTagCardNormal() {
    PastelmusicTheme {
        ArtistTagCard(
            artistName = "Tim Bergling",
            songCount = 20,
            artworkUri = PreviewData.createAlbum().albumArtPath,
            accentColor = Color(0xFF4CAF50),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Artist Tag Card - Different Accent", showBackground = true)
@Composable
fun PreviewArtistTagCardDifferentAccent() {
    PastelmusicTheme {
        ArtistTagCard(
            artistName = "Alan Olav Walker",
            songCount = 15,
            artworkUri = PreviewData.createAlbum().albumArtPath,
            accentColor = Color(0xFFFF9800),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Artist Tag Card - Hook Offset", showBackground = true)
@Composable
fun PreviewArtistTagCardHookOffset() {
    PastelmusicTheme {
        ArtistTagCard(
            artistName = "Rema",
            songCount = 12,
            artworkUri = PreviewData.createAlbum().albumArtPath,
            accentColor = Color(0xFF2196F3),
            hookOffset = 20.dp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Artist Tag Card - Long Name", showBackground = true)
@Composable
fun PreviewArtistTagCardLongName() {
    PastelmusicTheme {
        ArtistTagCard(
            artistName = "Artist With Very Long Name",
            songCount = 45,
            artworkUri = PreviewData.createAlbum().albumArtPath,
            accentColor = Color(0xFFE91E63),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Artist Tag Card - Purple Accent", showBackground = true)
@Composable
fun PreviewArtistTagCardPurple() {
    PastelmusicTheme {
        ArtistTagCard(
            artistName = "Wizkid",
            songCount = 33,
            artworkUri = PreviewData.createAlbum().albumArtPath,
            accentColor = Color(0xFF9C27B0),
            hookOffset = (-15).dp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
