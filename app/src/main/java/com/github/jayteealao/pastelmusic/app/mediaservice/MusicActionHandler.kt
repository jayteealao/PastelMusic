package com.github.jayteealao.pastelmusic.app.mediaservice

import android.content.Context
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.media3.common.Player
import androidx.media3.session.CommandButton
import androidx.media3.session.MediaSession
import androidx.media3.session.SessionCommand
import com.github.jayteealao.pastelmusic.app.R
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers.*
import com.github.jayteealao.pastelmusic.app.mediaservice.MusicCommands.REPEAT
import com.github.jayteealao.pastelmusic.app.mediaservice.MusicCommands.REPEAT_ONE
import com.github.jayteealao.pastelmusic.app.mediaservice.MusicCommands.REPEAT_SHUFFLE
import com.github.jayteealao.pastelmusic.app.mediaservice.MusicCommands.SHUFFLE
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.UNHANDLED_STATE_ERROR_MESSAGE
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.UNKNOWN_CUSTOM_ACTION_ERROR_MESSAGE
import com.github.jayteealao.pastelmusic.app.ui.icons.PastelIcons
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class MusicActionHandler @Inject constructor(
    @Dispatcher(MAIN) mainDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) {
    private val coroutineScope = CoroutineScope(mainDispatcher + SupervisorJob())

    private val customLayoutMap = mutableMapOf<String, CommandButton>()
    val customLayout: List<CommandButton> get() = customLayoutMap.values.toList()
    val customCommands = getAvailableCustomCommands()

    init {
        loadCustomLayout()
    }

    fun onCustomCommand(mediaSession: MediaSession, customCommand: SessionCommand) =
        when (customCommand.customAction) {
            REPEAT, REPEAT_ONE, SHUFFLE -> {
                handleRepeatShuffleCommand(
                    action = customCommand.customAction,
                    player = mediaSession.player
                )
            }
            else -> error("$UNKNOWN_CUSTOM_ACTION_ERROR_MESSAGE ${customCommand.customAction}")
        }

    fun cancelCoroutineScope() = coroutineScope.cancel()

    private fun handleRepeatShuffleCommand(action: String, player: Player) = when (action) {
        REPEAT -> {
            player.repeatMode = Player.REPEAT_MODE_ONE
            setRepeatShuffleCommand(REPEAT_ONE)
        }
        REPEAT_ONE -> {
            player.repeatMode = Player.REPEAT_MODE_ALL
            player.shuffleModeEnabled = true
            setRepeatShuffleCommand(SHUFFLE)
        }
        SHUFFLE -> {
            player.shuffleModeEnabled = false
            player.repeatMode = Player.REPEAT_MODE_ALL
            setRepeatShuffleCommand(REPEAT)
        }
        else -> error(UNHANDLED_STATE_ERROR_MESSAGE)
    }

    private fun setRepeatShuffleCommand(action: String) =
        customLayoutMap.set(REPEAT_SHUFFLE, customCommands.getValue(action))

    private fun loadCustomLayout() = customLayoutMap.run {
        this[REPEAT_SHUFFLE] = customCommands.getValue(REPEAT)
    }

    private fun getAvailableCustomCommands() = mapOf(
        REPEAT to buildCustomCommand(
            action = REPEAT,
            displayName = context.getString(R.string.repeat),
            iconResource = PastelIcons.Repeat.resourceId
        ),
        REPEAT_ONE to buildCustomCommand(
            action = REPEAT_ONE,
            displayName = context.getString(R.string.repeat_one),
            iconResource = PastelIcons.RepeatOne.resourceId
        ),
        SHUFFLE to buildCustomCommand(
            action = SHUFFLE,
            displayName = context.getString(R.string.shuffle),
            iconResource = PastelIcons.Shuffle.resourceId
        )
    )
}

private fun buildCustomCommand(
    action: String,
    displayName: String,
    @DrawableRes iconResource: Int,
) = CommandButton.Builder()
    .setSessionCommand(SessionCommand(action, Bundle.EMPTY))
    .setDisplayName(displayName)
    .setIconResId(iconResource)
    .build()
