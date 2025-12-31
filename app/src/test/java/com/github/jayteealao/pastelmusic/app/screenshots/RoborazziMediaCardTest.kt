package com.github.jayteealao.pastelmusic.app.screenshots

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.jayteealao.pastelmusic.app.components.MediaCard
import com.github.jayteealao.pastelmusic.app.components.Orientation
import com.github.jayteealao.pastelmusic.app.ui.preview.AlbumSpecs
import com.github.jayteealao.pastelmusic.app.ui.preview.SongSpecs
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi screenshot tests for MediaCard component.
 * These tests run on JVM with Robolectric and capture screenshots.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "w360dp-h640dp-xxhdpi")
class RoborazziMediaCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mediaCard_album_vertical_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    album = AlbumSpecs.normal.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/MediaCard_album_vertical.png"
        )
    }

    @Test
    fun mediaCard_album_horizontal_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    album = AlbumSpecs.normal.value,
                    orientation = Orientation.HORIZONTAL
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/MediaCard_album_horizontal.png"
        )
    }

    @Test
    fun mediaCard_song_vertical_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    song = SongSpecs.normal.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/MediaCard_song_vertical.png"
        )
    }

    @Test
    fun mediaCard_album_darkMode_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme(darkTheme = true) {
                MediaCard(
                    album = AlbumSpecs.normal.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/MediaCard_album_vertical_dark.png"
        )
    }
}
