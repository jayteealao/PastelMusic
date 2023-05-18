/*
 * Copyright 2022 Maximillian Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jayteealao.pastelmusic.app.mediaservice

import android.content.ComponentName
import android.content.Context
import androidx.media3.common.Player
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers.*
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.domain.usecase.GetPlayingQueueIndexUseCase
import com.github.jayteealao.pastelmusic.app.domain.usecase.GetPlayingQueueUseCase
import com.github.jayteealao.pastelmusic.app.domain.usecase.SetPlayingQueueIndexUseCase
import com.github.jayteealao.pastelmusic.app.domain.usecase.SetPlayingQueueUseCase
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_INDEX
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_POSITION_MS
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.POSITION_UPDATE_INTERVAL_MS
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicServiceConnection @Inject constructor(
    @ApplicationContext context: Context,
    @Dispatcher(MAIN) mainDispatcher: CoroutineDispatcher,
    private val getPlayingQueueUseCase: GetPlayingQueueUseCase,
    private val setPlayingQueueUseCase: SetPlayingQueueUseCase,
    private val getPlayingQueueIndexUseCase: GetPlayingQueueIndexUseCase,
    private val setPlayingQueueIndexUseCase: SetPlayingQueueIndexUseCase,
    private val playerListener: PlayerListener
) {
    private var mediaBrowser: MediaBrowser? = null
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val _playbackState = MutableStateFlow(PlaybackState())
    val playbackState = _playbackState.asStateFlow()

    var prevSongs: List<Song> = listOf()
    val index = getPlayingQueueIndexUseCase()


    val currentPosition = flow {
        while (currentCoroutineContext().isActive) {
            val currentPosition = mediaBrowser?.currentPosition ?: DEFAULT_POSITION_MS
            emit(currentPosition)
            delay(POSITION_UPDATE_INTERVAL_MS)
        }
    }

    init {
        coroutineScope.launch {
            mediaBrowser = MediaBrowser.Builder(
                context,
                SessionToken(context, ComponentName(context, MusicService::class.java))
            ).buildAsync().await().apply { addListener(playerListener) }
            playerListener.setPlaybackState(_playbackState)
            updatePlayingQueue()
        }
    }

    fun skipPrevious() = mediaBrowser?.run {
        seekToPrevious()
        play()
    }

    fun play() {
            if (mediaBrowser?.playbackState == Player.STATE_ENDED) {
                mediaBrowser?.run {
                    setMediaItems(prevSongs.map { it.mediaItem }, DEFAULT_INDEX, DEFAULT_POSITION_MS)
                    prepare()
                    play()
                }

            } else {
                mediaBrowser?.play()
            }
//        }

    }
    fun pause() = mediaBrowser?.pause()

    fun skipNext() = mediaBrowser?.run {
        seekToNext()
        play()
    }

    fun skipTo(position: Long) = mediaBrowser?.run {
        seekTo(position)
        play()
    }

    fun playSongs(
        songs: List<Song>,
        startIndex: Int = DEFAULT_INDEX,
        startPositionMs: Long = DEFAULT_POSITION_MS
    ) {
        mediaBrowser?.run {
            setMediaItems(songs.map { it.mediaItem }, startIndex, startPositionMs)
            prepare()
            play()
        }
        prevSongs = songs
        coroutineScope.launch { setPlayingQueueUseCase(songs) }
    }

    fun shuffleSongs(
        songs: List<Song>,
        startIndex: Int = DEFAULT_INDEX,
        startPositionMs: Long = DEFAULT_POSITION_MS
    ) = playSongs(
        songs = songs.shuffled(),
        startIndex = startIndex,
        startPositionMs = startPositionMs
    )

    private suspend fun updatePlayingQueue(startPositionMs: Long = DEFAULT_POSITION_MS) {
        val songs = getPlayingQueueUseCase().first()
        val startIndex = getPlayingQueueIndexUseCase().first()
        mediaBrowser?.run {
            setMediaItems(songs.map { it.mediaItem }, startIndex, startPositionMs)
            prepare()
        }
    }

    private fun updatePlayingQueueIndex(player: Player) = coroutineScope.launch {
        setPlayingQueueIndexUseCase(player.currentMediaItemIndex)
    }
}
