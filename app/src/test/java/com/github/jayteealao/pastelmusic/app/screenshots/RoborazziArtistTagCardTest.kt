package com.github.jayteealao.pastelmusic.app.screenshots

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.unit.dp
import com.github.takahirom.roborazzi.captureRoboImage
import com.github.jayteealao.pastelmusic.app.components.ArtistTagCard
import com.github.jayteealao.pastelmusic.app.ui.preview.PreviewData
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/**
 * Roborazzi screenshot tests for ArtistTagCard component.
 * Tests custom hook, artwork, and accent color variations.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33], qualifiers = "w360dp-h640dp-xxhdpi")
class RoborazziArtistTagCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun artistTagCard_greenAccent_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Tim Bergling",
                    songCount = 20,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFF4CAF50)
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_greenAccent.png"
        )
    }

    @Test
    fun artistTagCard_orangeAccent_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Alan Olav Walker",
                    songCount = 15,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFFFF9800)
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_orangeAccent.png"
        )
    }

    @Test
    fun artistTagCard_blueAccentWithOffset_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Rema",
                    songCount = 12,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFF2196F3),
                    hookOffset = 20.dp
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_blueAccentOffset.png"
        )
    }

    @Test
    fun artistTagCard_pinkAccentLongName_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Artist With A Very Long Name That Should Wrap",
                    songCount = 45,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFFE91E63)
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_pinkAccentLong.png"
        )
    }

    @Test
    fun artistTagCard_purpleAccentNegativeOffset_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Wizkid",
                    songCount = 33,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFF9C27B0),
                    hookOffset = (-15).dp
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_purpleAccentNegOffset.png"
        )
    }

    @Test
    fun artistTagCard_redAccentSingleSong_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Test Artist",
                    songCount = 1,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFFF44336)
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_redAccentSingle.png"
        )
    }

    @Test
    fun artistTagCard_tealAccentManySongs_roborazzi() {
        composeTestRule.setContent {
            PastelmusicTheme {
                ArtistTagCard(
                    artistName = "Prolific Artist",
                    songCount = 150,
                    artworkUri = PreviewData.createAlbum().albumArtPath,
                    accentColor = Color(0xFF009688)
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage(
            filePath = "src/test/snapshots/roborazzi/ArtistTagCard_tealAccentMany.png"
        )
    }
}
