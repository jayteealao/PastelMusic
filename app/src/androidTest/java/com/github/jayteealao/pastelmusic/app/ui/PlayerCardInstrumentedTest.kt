package com.github.jayteealao.pastelmusic.app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jayteealao.pastelmusic.app.components.PlayerCard
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.mediaservice.util.PlayerControl
import com.github.jayteealao.pastelmusic.app.ui.preview.PlaybackStateSpecs
import com.github.jayteealao.pastelmusic.app.ui.preview.PreviewPlayerControl
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented UI tests for PlayerCard component.
 * These tests run on a device or emulator.
 */
@RunWith(AndroidJUnit4::class)
class PlayerCardInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun playerCard_displaysTitle() {
        val state = PlaybackStateSpecs.playing.value

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = state,
                    controls = PreviewPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithText(state.song.title).assertIsDisplayed()
    }

    @Test
    fun playerCard_displaysArtist() {
        val state = PlaybackStateSpecs.playing.value

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = state,
                    controls = PreviewPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithText(state.song.artist).assertIsDisplayed()
    }

    @Test
    fun playerCard_displaysAlbum() {
        val state = PlaybackStateSpecs.playing.value

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = state,
                    controls = PreviewPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithText(state.song.album).assertIsDisplayed()
    }

    @Test
    fun playerCard_playButtonClickTriggersPlay() {
        var playClicked = false
        val controls = object : PlayerControl {
            override fun play() { playClicked = true }
            override fun pause() {}
            override fun skipNext() {}
            override fun skipPrevious() {}
        }

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlaybackStateSpecs.paused.value,
                    controls = controls
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Play").performClick()
        assert(playClicked)
    }

    @Test
    fun playerCard_pauseButtonClickTriggersPause() {
        var pauseClicked = false
        val controls = object : PlayerControl {
            override fun play() {}
            override fun pause() { pauseClicked = true }
            override fun skipNext() {}
            override fun skipPrevious() {}
        }

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlaybackStateSpecs.playing.value,
                    controls = controls
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Pause").performClick()
        assert(pauseClicked)
    }

    @Test
    fun playerCard_previousButtonClickTriggersSkipPrevious() {
        var skipPreviousClicked = false
        val controls = object : PlayerControl {
            override fun play() {}
            override fun pause() {}
            override fun skipNext() {}
            override fun skipPrevious() { skipPreviousClicked = true }
        }

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlaybackStateSpecs.playing.value,
                    controls = controls
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Previous").performClick()
        assert(skipPreviousClicked)
    }

    @Test
    fun playerCard_nextButtonClickTriggersSkipNext() {
        var skipNextClicked = false
        val controls = object : PlayerControl {
            override fun play() {}
            override fun pause() {}
            override fun skipNext() { skipNextClicked = true }
            override fun skipPrevious() {}
        }

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlaybackStateSpecs.playing.value,
                    controls = controls
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Next").performClick()
        assert(skipNextClicked)
    }
}
