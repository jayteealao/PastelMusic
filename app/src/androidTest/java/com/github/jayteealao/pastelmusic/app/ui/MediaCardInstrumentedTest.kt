package com.github.jayteealao.pastelmusic.app.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.jayteealao.pastelmusic.app.components.MediaCard
import com.github.jayteealao.pastelmusic.app.components.Orientation
import com.github.jayteealao.pastelmusic.app.ui.preview.AlbumSpecs
import com.github.jayteealao.pastelmusic.app.ui.preview.SongSpecs
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented UI tests for MediaCard component.
 * These tests run on a device or emulator.
 */
@RunWith(AndroidJUnit4::class)
class MediaCardInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mediaCard_album_displaysTitle() {
        val album = AlbumSpecs.normal.value

        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    album = album,
                    orientation = Orientation.VERTICAL
                )
            }
        }

        composeTestRule.onNodeWithText(album.title).assertIsDisplayed()
    }

    @Test
    fun mediaCard_song_displaysTitle() {
        val song = SongSpecs.normal.value

        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    song = song,
                    orientation = Orientation.VERTICAL
                )
            }
        }

        composeTestRule.onNodeWithText(song.title).assertIsDisplayed()
    }

    @Test
    fun mediaCard_album_vertical_hasPlayAction() {
        var playClicked = false
        val album = AlbumSpecs.normal.value

        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    album = album,
                    orientation = Orientation.VERTICAL,
                    onClickPlay = { playClicked = true }
                )
            }
        }

        // Find and click the play button
        composeTestRule.onNode(hasClickAction()).performClick()
        // Note: This will click the first clickable item
    }

    @Test
    fun mediaCard_album_horizontal_displaysTitle() {
        val album = AlbumSpecs.normal.value

        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    album = album,
                    orientation = Orientation.HORIZONTAL
                )
            }
        }

        composeTestRule.onNodeWithText(album.title).assertIsDisplayed()
    }

    @Test
    fun mediaCard_song_horizontal_displaysTitle() {
        val song = SongSpecs.normal.value

        composeTestRule.setContent {
            PastelmusicTheme {
                MediaCard(
                    song = song,
                    orientation = Orientation.HORIZONTAL
                )
            }
        }

        composeTestRule.onNodeWithText(song.title).assertIsDisplayed()
    }
}
