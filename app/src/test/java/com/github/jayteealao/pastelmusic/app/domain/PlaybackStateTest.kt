package com.github.jayteealao.pastelmusic.app.domain

import androidx.media3.common.Player
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Unit tests for PlaybackState data class.
 */
class PlaybackStateTest {

    @Test
    fun `isPlaying returns true when state is READY and playWhenReady is true`() {
        val state = PlaybackState(
            playbackState = Player.STATE_READY,
            playWhenReady = true
        )
        assertThat(state.isPlaying).isTrue()
    }

    @Test
    fun `isPlaying returns true when state is BUFFERING and playWhenReady is true`() {
        val state = PlaybackState(
            playbackState = Player.STATE_BUFFERING,
            playWhenReady = true
        )
        assertThat(state.isPlaying).isTrue()
    }

    @Test
    fun `isPlaying returns false when state is READY but playWhenReady is false`() {
        val state = PlaybackState(
            playbackState = Player.STATE_READY,
            playWhenReady = false
        )
        assertThat(state.isPlaying).isFalse()
    }

    @Test
    fun `isPlaying returns false when state is IDLE`() {
        val state = PlaybackState(
            playbackState = Player.STATE_IDLE,
            playWhenReady = true
        )
        assertThat(state.isPlaying).isFalse()
    }

    @Test
    fun `isPlaying returns false when state is ENDED`() {
        val state = PlaybackState(
            playbackState = Player.STATE_ENDED,
            playWhenReady = true
        )
        assertThat(state.isPlaying).isFalse()
    }

    @Test
    fun `default PlaybackState is not playing`() {
        val state = PlaybackState()
        assertThat(state.isPlaying).isFalse()
    }

    @Test
    fun `PlaybackState with song maintains song data`() {
        val song = TestData.createSong(title = "Test Song")
        val state = PlaybackState(
            song = song
        )
        assertThat(state.song.title).isEqualTo("Test Song")
    }
}
