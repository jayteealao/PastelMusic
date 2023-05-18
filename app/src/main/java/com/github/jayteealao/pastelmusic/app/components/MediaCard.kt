package com.github.jayteealao.pastelmusic.app.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.ui.components.Hook
import com.github.jayteealao.pastelmusic.app.ui.components.PastelLayout

// use shared transition to transit from vertical media card to horizontal media card
@Composable
fun MediaCard(album: Album, modifier: Modifier = Modifier, orientation: Orientation = Orientation.VERTICAL, onClickPlay: () -> Unit = {}) {
    if (orientation == Orientation.VERTICAL) {
        MediaCardVertical(album = album, modifier = modifier, onClickPlay = onClickPlay)
    } else {
        MediaCardHorizontal(album = album, modifier = modifier, onClickPlay = onClickPlay)
    }
}

@Composable
fun MediaCard(song: Song, modifier: Modifier = Modifier, orientation: Orientation = Orientation.VERTICAL, onClickPlay: () -> Unit = {}) {
    if (orientation == Orientation.VERTICAL) {
        MediaCardVertical(song = song, modifier = modifier, onClickPlay = onClickPlay)
    } else {
        MediaCardHorizontal(song = song, modifier = modifier, onClickPlay = onClickPlay)
    }
}

@Composable
private fun MediaCardVertical(album: Album, modifier: Modifier = Modifier, onClickPlay: () -> Unit = {}) {
    val details = MediaCardDetails(
        title = album.title ?: "",
        addInfo = album.title ?: "",
        albumArtPath = album.albumArtPath ?: Uri.EMPTY,
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Hook(
            Modifier
                .height(48.dp)
                .width(16.dp)
                .offset(x = 16.dp, y = 20.dp)
                .zIndex(10f))
        MediaCardContentVertical(details = details, onClickPlay = onClickPlay)
    }
}

@Composable
private fun MediaCardVertical(song: Song, modifier: Modifier = Modifier, onClickPlay: () -> Unit = {}) {
    val details = MediaCardDetails(
        title = song.title ?: "",
        addInfo = song.title ?: "",
        albumArtPath = song.albumArtPath ?: Uri.EMPTY,
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Hook(
            Modifier
                .height(48.dp)
                .width(16.dp)
                .offset(x = 16.dp, y = 20.dp)
                .zIndex(10f))
        MediaCardContentVertical(details = details, onClickPlay = onClickPlay)
    }
}

@Composable
private fun MediaCardContentVertical(details: MediaCardDetails, onClickPlay: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .wrapContentSize(),
    ) {
        PastelLayout(modifier = Modifier
            .width(160.dp)
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PastelArtworkImage(
//                            model = LocalContext.current.contentResolver.loadThumbnail("content://media/external/audio/media/176102".toUri(), Size(140, 140), CancellationSignal()),
                        artworkUri = details.albumArtPath,
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .size(140.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            text = details.title,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(Color.Black)
                                .clickable { onClickPlay() }
                        )
                    }
                    Text(
                        text = details.addInfo,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
data class MediaCardDetails(
    val title: String,
    val addInfo: String,
    val albumArtPath: Uri
)

@Composable
private fun MediaCardHorizontal(album: Album, modifier: Modifier = Modifier, onClickPlay: () -> Unit = {}) {
    val details = MediaCardDetails(
        title = album.title ?: "",
        addInfo = album.title ?: "",
        albumArtPath = album.albumArtPath ?: Uri.EMPTY,
    )
    Row (
        modifier = modifier
            .height(160.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Hook(
            Modifier
                .height(48.dp)
                .width(16.dp)
                .offset(x = 7.dp, y = (-52).dp)
                .zIndex(10f)
                .rotate(90f)
        )
        PastelLayout(
            modifier = Modifier
//                .graphicsLayer {
//                    rotationZ = 5f
//                }
        ) {
            MediaContentHorizontal(details = details, onClickPlay = onClickPlay)
        }

    }
}

@Composable
private fun MediaCardHorizontal(song: Song, modifier: Modifier = Modifier, onClickPlay: () -> Unit = {}) {
    val details = MediaCardDetails(
        title = song.title ?: "",
        addInfo = song.title ?: "",
        albumArtPath = song.albumArtPath ?: Uri.EMPTY,
    )
    Row (
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Hook(
            Modifier
                .height(48.dp)
                .width(16.dp)
                .offset(x = 7.dp, y = 0.dp)
                .zIndex(10f)
                .rotate(90f)
        )
        PastelLayout(
            modifier = Modifier
//                .graphicsLayer {
//                    rotationZ = 5f
//                }
        ) {
            MediaContentHorizontal(details = details, onClickPlay = onClickPlay)
        }
    }
}

@Composable
private fun MediaContentHorizontal(
    details: MediaCardDetails,
    onClickPlay: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .width(340.dp)
            .height(100.dp)
            .padding(12.dp)
    ) {
        PastelArtworkImage(
            artworkUri = details.albumArtPath,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(60.dp)
                .padding(end = 12.dp)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = details.title,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = details.addInfo,
                style = MaterialTheme.typography.subtitle1,
            )
        }
        Text(
            text = "3:40",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .align(Alignment.Bottom)
        )
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .size(20.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .background(Color.Black)
        )
    }
}
enum class Orientation { VERTICAL, HORIZONTAL}
@Preview
@Composable
fun PreviewAlbumCard() {
//    MediaCard()
}