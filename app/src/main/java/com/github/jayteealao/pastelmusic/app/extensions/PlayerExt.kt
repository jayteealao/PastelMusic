package com.github.jayteealao.pastelmusic.app.extensions

import androidx.media3.common.Player

inline val Player.isPlayEnabled
    get() = (availableCommands.contains(Player.COMMAND_PLAY_PAUSE)) &&
            (!playWhenReady)

inline val Player.isEnded
    get() = playbackState == Player.STATE_ENDED