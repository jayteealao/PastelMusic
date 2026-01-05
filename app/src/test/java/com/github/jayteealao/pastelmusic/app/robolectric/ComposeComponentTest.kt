package com.github.jayteealao.pastelmusic.app.robolectric

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.github.jayteealao.pastelmusic.app.components.PlayerCard
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Robolectric tests for Compose components.
 * These tests run on JVM with Android framework stubs.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ComposeComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `PlayerCard displays song title`() {
        val song = TestData.createSong(
            title = "Test Song Title",
            artist = "Test Artist",
            album = "Test Album"
        )
        val playbackState = TestData.createPlaybackState(song = song)

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = playbackState,
                    controls = TestData.noOpPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithText("Test Song Title").assertIsDisplayed()
    }

    @Test
    fun `PlayerCard displays artist name`() {
        val song = TestData.createSong(
            title = "Test Song",
            artist = "Famous Artist",
            album = "Best Album"
        )
        val playbackState = TestData.createPlaybackState(song = song)

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = playbackState,
                    controls = TestData.noOpPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithText("Famous Artist").assertIsDisplayed()
    }

    @Test
    fun `PlayerCard displays album name`() {
        val song = TestData.createSong(
            title = "Test Song",
            artist = "Artist Name",
            album = "Amazing Album"
        )
        val playbackState = TestData.createPlaybackState(song = song)

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = playbackState,
                    controls = TestData.noOpPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithText("Amazing Album").assertIsDisplayed()
    }

    @Test
    fun `PlayerCard shows play button when not playing`() {
        val playbackState = TestData.createPlaybackState(isPlaying = false)

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = playbackState,
                    controls = TestData.noOpPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Play").assertIsDisplayed()
    }

    @Test
    fun `PlayerCard shows pause button when playing`() {
        val playbackState = TestData.createPlaybackState(isPlaying = true)

        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = playbackState,
                    controls = TestData.noOpPlayerControl
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Pause").assertIsDisplayed()
    }
}
