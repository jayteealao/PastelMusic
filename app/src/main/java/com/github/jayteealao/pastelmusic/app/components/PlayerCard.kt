package com.github.jayteealao.pastelmusic.app.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.github.jayteealao.pastelmusic.app.domain.model.EMPTY_SONG
import com.github.jayteealao.pastelmusic.app.domain.model.formattedDuration
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.mediaservice.util.PlayerControl
import com.github.jayteealao.pastelmusic.app.mediaservice.util.orDefaultTimestamp
import com.github.jayteealao.pastelmusic.app.ui.components.Hook
import com.github.jayteealao.pastelmusic.app.ui.components.PastelLayout
import com.github.jayteealao.pastelmusic.app.ui.icons.PastelIcons

@Composable
fun PlayerCard(modifier: Modifier = Modifier, playbackState: PlaybackState = PlaybackState(), controls: PlayerControl, ) {
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
               .rotate(115f)
       )
       PastelLayout(
           modifier = Modifier
               .graphicsLayer {
                   rotationZ = 5f
               }
       ) {
           PlayerContent(playbackState, controls)
       }
       Hook(
           Modifier
               .height(48.dp)
               .width(16.dp)
               .offset(x = (-5).dp, y = 49.dp)
               .zIndex(1000f)
               .rotate(115f)
       )
   }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PlayerContent(playbackState: PlaybackState, controls: PlayerControl) {
//    LaunchedEffect(playbackState.song) {
//        Log.d("player", "${playbackState.song}")
//    }
    var play by remember {
        mutableStateOf(playbackState.isPlaying)
    }
    Column {
        //region MediaInfo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(344.dp)
                .height(96.dp)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp)
        ) {
            PastelArtworkImage(
                artworkUri = playbackState.song.albumArtPath,
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(180.dp)
                    .align(Alignment.Top),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = playbackState.song.title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = playbackState.song.album,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    text = playbackState.song.artist,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Spacer(modifier = Modifier)
            Text(
                text = playbackState.song.duration.orDefaultTimestamp().formattedDuration(),
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .wrapContentWidth()
            )
        }
        //endregion
        // region player controls
        Row(modifier = Modifier
            .height(32.dp)
            .align(Alignment.CenterHorizontally)) {
            IconButton(onClick = { controls.skipPrevious() }) {
                Icon(painter = painterResource(PastelIcons.SkipPrevious.resourceId), contentDescription = "Previous")
            }
//            IconToggleButton(
//                checked = playbackState.isPlaying,
//                enabled = true,
//                onCheckedChange = {
//                    Log.d("player-msc", "we in button")
//
//                    /*if (playbackState.isPlaying) */controls.pause() /*else controls.play()*/
//                }
//            ) {
                AnimatedContent(playbackState.isPlaying) {
                    when(it) {
                        false -> IconButton(onClick = { controls.play() }) {
                            Icon(
                                painter = painterResource(PastelIcons.Play.resourceId),
                                contentDescription = "Play"
                            )
                        }
                        true -> IconButton(onClick = { controls.pause() }) {
                            Icon(
                                painter = painterResource(PastelIcons.Pause.resourceId),
                                contentDescription = "Pause"
                            )
                        }
                    }
                }
//            }
            IconButton(onClick = { controls.skipNext() }) {
                Icon(painter = painterResource(PastelIcons.SkipNext.resourceId), contentDescription = "Next")
            }
        }
        //endregion
    }

}

@Preview
@Composable
fun PreviewBottom() {
    PlayerCard(
        playbackState = PlaybackState(
            song = EMPTY_SONG.copy(
                title = "Going Home to my mama",
                album = "Bandana",
                artist = "johnny drille"
            )
        ),
        controls = object : PlayerControl {
        override fun play() = Unit
        override fun pause() = Unit
        override fun skipNext() = Unit
        override fun skipPrevious() = Unit
    }
    )
}