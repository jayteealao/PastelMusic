package com.github.jayteealao.pastelmusic.app.ui.preview

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.github.jayteealao.pastelmusic.app.components.MediaCard
import com.github.jayteealao.pastelmusic.app.components.Orientation
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme

/**
 * Compose previews for MediaCard component.
 * Uses PreviewParameterProvider for state enumeration.
 */

@Preview(
    name = "MediaCard Album - Vertical - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun MediaCardAlbumVerticalPreview(
    @PreviewParameter(AlbumPreviewProvider::class) album: Album
) {
    PastelmusicTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                album = album,
                orientation = Orientation.VERTICAL
            )
        }
    }
}

@Preview(
    name = "MediaCard Album - Horizontal - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun MediaCardAlbumHorizontalPreview(
    @PreviewParameter(AlbumPreviewProvider::class) album: Album
) {
    PastelmusicTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                album = album,
                orientation = Orientation.HORIZONTAL
            )
        }
    }
}

@Preview(
    name = "MediaCard Album - Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MediaCardAlbumDarkPreview(
    @PreviewParameter(AlbumPreviewProvider::class) album: Album
) {
    PastelmusicTheme(darkTheme = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                album = album,
                orientation = Orientation.VERTICAL
            )
        }
    }
}

@Preview(
    name = "MediaCard Song - Vertical - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun MediaCardSongVerticalPreview(
    @PreviewParameter(SongPreviewProvider::class) song: Song
) {
    PastelmusicTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                song = song,
                orientation = Orientation.VERTICAL
            )
        }
    }
}

@Preview(
    name = "MediaCard Song - Horizontal - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun MediaCardSongHorizontalPreview(
    @PreviewParameter(SongPreviewProvider::class) song: Song
) {
    PastelmusicTheme(darkTheme = false) {
        Column(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                song = song,
                orientation = Orientation.HORIZONTAL
            )
        }
    }
}

@Preview(
    name = "MediaCard Song - Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun MediaCardSongDarkPreview(
    @PreviewParameter(SongPreviewProvider::class) song: Song
) {
    PastelmusicTheme(darkTheme = true) {
        Column(modifier = Modifier.padding(16.dp)) {
            MediaCard(
                song = song,
                orientation = Orientation.VERTICAL
            )
        }
    }
}
