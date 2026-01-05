package com.github.jayteealao.pastelmusic.app.screenshots

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.jayteealao.pastelmusic.app.components.PlayerCard
import com.github.jayteealao.pastelmusic.app.ui.preview.PlayerCardSpecs
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi screenshot tests for PlayerCard component.
 * These tests run on JVM with Robolectric and capture screenshots.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "w360dp-h640dp-xxhdpi")
class RoborazziPlayerCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun playerCard_idle_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.idle.value.playbackState,
                    controls = PlayerCardSpecs.idle.value.controls
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/PlayerCard_idle.png"
        )
    }

    @Test
    fun playerCard_playing_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.playing.value.playbackState,
                    controls = PlayerCardSpecs.playing.value.controls
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/PlayerCard_playing.png"
        )
    }

    @Test
    fun playerCard_paused_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.paused.value.playbackState,
                    controls = PlayerCardSpecs.paused.value.controls
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/PlayerCard_paused.png"
        )
    }

    @Test
    fun playerCard_darkMode_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme(darkTheme = true) {
                PlayerCard(
                    playbackState = PlayerCardSpecs.playing.value.playbackState,
                    controls = PlayerCardSpecs.playing.value.controls
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/PlayerCard_playing_dark.png"
        )
    }
}
