package com.github.jayteealao.pastelmusic.app.mediaservice.util

interface PlayerControl {
    fun play(): Unit?
    fun pause(): Unit?
    fun skipNext(): Unit?
    fun skipPrevious(): Unit?
}
