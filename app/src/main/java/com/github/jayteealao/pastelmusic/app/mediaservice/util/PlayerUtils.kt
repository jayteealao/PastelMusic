package com.github.jayteealao.pastelmusic.app.mediaservice.util
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


import androidx.media3.common.C
import androidx.media3.common.Player
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.INVALID_PLAYBACK_STATE_ERROR_MESSAGE

internal fun Int.asPlaybackState() = when (this) {
    Player.STATE_IDLE -> PlaybackState()
    Player.STATE_BUFFERING -> PlaybackState(Player.STATE_BUFFERING, true)
    Player.STATE_READY -> PlaybackState(Player.STATE_READY, true)
    Player.STATE_ENDED -> PlaybackState(Player.STATE_ENDED)
    else -> error(INVALID_PLAYBACK_STATE_ERROR_MESSAGE)
}

internal fun Long.orDefaultTimestamp() = takeIf { it != C.TIME_UNSET && it > 0} ?: 0L
