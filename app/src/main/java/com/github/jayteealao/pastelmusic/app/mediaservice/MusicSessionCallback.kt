package com.github.jayteealao.pastelmusic.app.mediaservice

import android.os.Bundle
import androidx.media3.common.MediaItem
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import androidx.media3.session.SessionResult
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.usecase.SearchMediaByIdUseCase
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.guava.asListenableFuture

class MusicSessionCallback @Inject constructor(
    @Dispatcher(PastelDispatchers.MAIN) mainDispatcher: CoroutineDispatcher,
    private val musicActionHandler: MusicActionHandler,
    private val searchMediaByIdUseCase: SearchMediaByIdUseCase,
) : MediaLibraryService.MediaLibrarySession.Callback {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    override fun onGetItem(
        session: MediaLibraryService.MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        mediaId: String
    ): ListenableFuture<LibraryResult<MediaItem>> {
        return coroutineScope.async {
            LibraryResult.ofItem(
                searchMediaByIdUseCase(mediaId).first().mediaItem ?: MediaItem.EMPTY,
                MediaLibraryService.LibraryParams.Builder().build())
        }.asListenableFuture()

    }

    override fun onAddMediaItems(
        mediaSession: MediaSession,
        controller: MediaSession.ControllerInfo,
        mediaItems: List<MediaItem>
    ): ListenableFuture<List<MediaItem>> = Futures.immediateFuture(
        mediaItems.map { mediaItem ->
            mediaItem.buildUpon()
                .setUri(mediaItem.requestMetadata.mediaUri)
                .build()
        }
    )

    override fun onConnect(
        session: MediaSession,
        controller: MediaSession.ControllerInfo
    ): MediaSession.ConnectionResult {
        val connectionResult = super.onConnect(session, controller)
        val availableSessionCommands = connectionResult.availableSessionCommands.buildUpon()
        musicActionHandler.customCommands.values.forEach { commandButton ->
            commandButton.sessionCommand?.let(availableSessionCommands::add)
        }

        return MediaSession.ConnectionResult.accept(
            availableSessionCommands.build(),
            connectionResult.availablePlayerCommands
        )
    }

    override fun onPostConnect(session: MediaSession, controller: MediaSession.ControllerInfo) {
        session.setCustomLayout(controller, musicActionHandler.customLayout)
    }

    override fun onCustomCommand(
        session: MediaSession,
        controller: MediaSession.ControllerInfo,
        customCommand: SessionCommand,
        args: Bundle
    ): ListenableFuture<SessionResult> {
        musicActionHandler.onCustomCommand(mediaSession = session, customCommand = customCommand)
        session.setCustomLayout(musicActionHandler.customLayout)
        return Futures.immediateFuture(SessionResult(SessionResult.RESULT_SUCCESS))
    }

    fun cancelCoroutineScope() {
        coroutineScope.cancel()
        musicActionHandler.cancelCoroutineScope()
    }
}