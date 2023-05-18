package com.github.jayteealao.pastelmusic.app.model

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED
import androidx.media3.common.PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT
import androidx.media3.common.Player
import androidx.media3.common.Player.EVENT_MEDIA_ITEM_TRANSITION
import androidx.media3.common.Player.EVENT_MEDIA_METADATA_CHANGED
import androidx.media3.common.Player.EVENT_PLAYBACK_STATE_CHANGED
import androidx.media3.common.Player.EVENT_PLAY_WHEN_READY_CHANGED
import androidx.media3.session.MediaBrowser
import androidx.media3.session.MediaController
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionToken
import com.github.jayteealao.pastelmusic.app.database.SongsRepository
import com.github.jayteealao.pastelmusic.app.mediaservice.EMPTY_PLAYBACK_STATE
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.player.PastelMediaLibraryService
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class ActivityViewModel @Inject constructor(@ApplicationContext context: Context, private val songRepo: SongsRepository): ViewModel() {
    private val rootMediaItem = MutableLiveData<MediaItem>()
        .apply { postValue(MediaItem.EMPTY) }
    private val playbackState = MutableLiveData<PlaybackState>()
        .apply { postValue(EMPTY_PLAYBACK_STATE) }
    private val nowPlaying = MutableLiveData<MediaItem>()
        .apply { postValue(NOTHING_PLAYING) }
    val player: Player? get() = browser
    val networkFailure = MutableLiveData<Boolean>()
        .apply { postValue(false) }
    var browser: MediaBrowser? = null
    private val playerListener: PlayerListener = PlayerListener()
    init {
        viewModelScope.launch {
            val newBrowser =
                MediaBrowser.Builder(
                    context,
                    SessionToken(
                        context,
                        ComponentName(
                            context,
                            PastelMediaLibraryService::class.java
                        )
                    )
                )
                    .setListener(BrowserListener())
                    .buildAsync()
                    .await()
            newBrowser.addListener(playerListener)
            browser = newBrowser
            rootMediaItem.postValue(
                newBrowser.getLibraryRoot(/* params= */ null).await().value
            )
            newBrowser.currentMediaItem?.let {
                nowPlaying.postValue(it)
            }
            Log.d("rootMediaItem", "${rootMediaItem.value}")
        }
    }
    fun setMediaBrowser(mediaBrowser: MediaBrowser) {
        mediaBrowser.addListener(playerListener)
        browser = mediaBrowser
        viewModelScope.launch {
            rootMediaItem.postValue(
                mediaBrowser.getLibraryRoot(/* params= */ null).await().value
            )
        }
        mediaBrowser.currentMediaItem?.let {
            nowPlaying.postValue(it)
        }
    }

    fun testBed() {
        Log.d("musicserviceconnection", "${browser?.isConnected}")
        viewModelScope.launch(Dispatchers.IO) {

//            while (browser == null) {
//
//            }
            delay(500)
            withContext(Dispatchers.Main) {
                Log.d("musicserviceconnectionr", "${browser?.getLibraryRoot(null)?.await()?.value?.mediaId}")
                Log.d("musicserviceconnectionk", "${rootMediaItem.value?.mediaId?.let {
                    browser?.getChildren(
                        it, 0, 100, null
                    )?.await()?.value?.map { it.mediaMetadata.title.toString() }
                }}")
            }

        }
    }

    suspend fun getChildren(parentId: String): ImmutableList<MediaItem> {
        return this.browser?.getChildren(parentId, 0, 100, null)?.await()?.value
            ?: ImmutableList.of()
    }

    suspend fun sendCommand(command: String, parameters: Bundle?):Boolean =
        sendCommand(command, parameters) { _, _ -> }

    suspend fun sendCommand(
        command: String,
        parameters: Bundle?,
        resultCallback: ((Int, Bundle?) -> Unit)
    ):Boolean = if (browser?.isConnected == true) {
        val args = parameters ?: Bundle()
        browser?.sendCustomCommand(SessionCommand(command, args), args)?.await()?.let {
            resultCallback(it.resultCode, it.extras)
        }
        true
    } else {
        false
    }

    private fun updatePlaybackState(player: Player) {
        playbackState.postValue(
            PlaybackState(
                player.playbackState,
                player.playWhenReady,
                player.duration
            )
        )
    }

    private fun updateNowPlaying(player: Player) {
        val mediaItem = player.currentMediaItem ?: MediaItem.EMPTY
        if (mediaItem == MediaItem.EMPTY) {
            return
        }
        // The current media item from the CastPlayer may have lost some information.
        val mediaItemFuture = browser!!.getItem(mediaItem.mediaId)
        mediaItemFuture.addListener(
            Runnable {
                val fullMediaItem = mediaItemFuture.get().value ?: return@Runnable
                nowPlaying.postValue(
                    mediaItem.buildUpon().setMediaMetadata(fullMediaItem.mediaMetadata).build()
                )
            },
            MoreExecutors.directExecutor()
        )
    }


    private inner class BrowserListener : MediaBrowser.Listener {
        override fun onDisconnected(controller: MediaController) {
            release()
        }
    }

    fun release() {
        rootMediaItem.postValue(MediaItem.EMPTY)
        nowPlaying.postValue(NOTHING_PLAYING)
        browser?.let {
            it.removeListener(playerListener)
            it.release()
        }
    }

    inner class PlayerListener : Player.Listener {
        override fun onEvents(player: Player, events: Player.Events) {
            if (events.contains(EVENT_PLAY_WHEN_READY_CHANGED)
                || events.contains(EVENT_PLAYBACK_STATE_CHANGED)
                || events.contains(EVENT_MEDIA_ITEM_TRANSITION)) {
                updatePlaybackState(player)
                if (player.playbackState != Player.STATE_IDLE) {
                    networkFailure.postValue(false)
                }
            }
            if (events.contains(EVENT_MEDIA_METADATA_CHANGED)
                || events.contains(EVENT_MEDIA_ITEM_TRANSITION)
                || events.contains(EVENT_PLAY_WHEN_READY_CHANGED)) {
                updateNowPlaying(player)
            }
        }

        override fun onPlayerErrorChanged(error: PlaybackException?) {
            when(error?.errorCode) {
                ERROR_CODE_IO_BAD_HTTP_STATUS,
                ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE,
                ERROR_CODE_IO_CLEARTEXT_NOT_PERMITTED,
                ERROR_CODE_IO_NETWORK_CONNECTION_FAILED,
                ERROR_CODE_IO_NETWORK_CONNECTION_TIMEOUT -> {
                    networkFailure.postValue(true)
                }
            }
        }
    }
}

val NOTHING_PLAYING: MediaItem = MediaItem.EMPTY