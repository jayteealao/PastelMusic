package com.github.jayteealao.pastelmusic.app.mediaservice

import androidx.media3.common.C
import androidx.media3.common.Player
import com.github.jayteealao.pastelmusic.app.domain.model.EMPTY_SONG
import com.github.jayteealao.pastelmusic.app.domain.model.Song

data class PlaybackState(
    val playbackState: Int = Player.STATE_IDLE,
    val playWhenReady: Boolean = false,
    val duration: Long = C.TIME_UNSET,
    val song: Song = EMPTY_SONG
) {
    val isPlaying: Boolean
        get() {
            return (playbackState == Player.STATE_BUFFERING
                    || playbackState == Player.STATE_READY)
                    && playWhenReady
        }
}

val EMPTY_PLAYBACK_STATE: PlaybackState = PlaybackState()
