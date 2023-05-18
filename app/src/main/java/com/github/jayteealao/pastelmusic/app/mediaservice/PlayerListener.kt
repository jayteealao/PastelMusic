package com.github.jayteealao.pastelmusic.app.mediaservice

import androidx.media3.common.Player
import com.github.jayteealao.pastelmusic.app.datasource.mapper.toSongModel
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.EMPTY_SONG
import com.github.jayteealao.pastelmusic.app.domain.usecase.SetPlayingQueueIndexUseCase
import com.github.jayteealao.pastelmusic.app.mediaservice.util.orDefaultTimestamp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayerListener @Inject constructor(
    private val setPlayingQueueIndexUseCase: SetPlayingQueueIndexUseCase,
    @Dispatcher(PastelDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,

    ) : Player.Listener {

    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())
    private var _playbackState : MutableStateFlow<PlaybackState>? = null
    private var currentSong = EMPTY_SONG

    override fun onEvents(player: Player, events: Player.Events) {
        if (events.containsAny(
                Player.EVENT_PLAYBACK_STATE_CHANGED,
                Player.EVENT_MEDIA_METADATA_CHANGED,
                Player.EVENT_PLAY_WHEN_READY_CHANGED,
                Player.EVENT_MEDIA_ITEM_TRANSITION
            )
        ) {
            updatePlaybackState(player, _playbackState!!)
        }

        if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)) {
            coroutineScope.launch{
                setPlayingQueueIndexUseCase(player.currentMediaItemIndex)
            }
        }
    }

    private fun updatePlaybackState(player: Player, playbackState: MutableStateFlow<PlaybackState>) = with(player) {
        playbackState.update {
            it.copy(
                playbackState = this.playbackState,
                playWhenReady = playWhenReady,
                duration = duration.orDefaultTimestamp(),
                song = currentMediaItem?.toSongModel() ?: currentSong
            )
        }
    }

    fun setPlaybackState(playbackState: MutableStateFlow<PlaybackState>) {
        _playbackState = playbackState
    }
}
