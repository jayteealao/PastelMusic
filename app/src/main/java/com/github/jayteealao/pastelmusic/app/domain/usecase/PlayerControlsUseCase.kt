package com.github.jayteealao.pastelmusic.app.domain.usecase

import com.github.jayteealao.pastelmusic.app.mediaservice.MusicServiceConnection
import com.github.jayteealao.pastelmusic.app.mediaservice.util.PlayerControl
import javax.inject.Inject

class PlayerControlsUseCase @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) {

    operator fun invoke()  = object : PlayerControl {
        override fun play() = musicServiceConnection.play()
        override fun pause() = musicServiceConnection.pause()
        override fun skipNext() = musicServiceConnection.skipNext()
        override fun skipPrevious() = musicServiceConnection.skipPrevious()
    }
}