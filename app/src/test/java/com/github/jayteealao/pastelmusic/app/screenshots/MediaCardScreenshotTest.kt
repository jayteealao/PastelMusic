package com.github.jayteealao.pastelmusic.app.screenshots

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.github.jayteealao.pastelmusic.app.components.MediaCard
import com.github.jayteealao.pastelmusic.app.components.Orientation
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.github.jayteealao.pastelmusic.app.ui.preview.AlbumSpecs
import com.github.jayteealao.pastelmusic.app.ui.preview.SongSpecs
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test

/**
 * Paparazzi screenshot tests for MediaCard component.
 * These tests capture JVM-based screenshots for visual regression testing.
 */
class MediaCardScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        showSystemUi = false
    )

    @Test
    fun mediaCard_album_vertical() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MediaCard(
                    album = AlbumSpecs.normal.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }
    }

    @Test
    fun mediaCard_album_horizontal() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MediaCard(
                    album = AlbumSpecs.normal.value,
                    orientation = Orientation.HORIZONTAL
                )
            }
        }
    }

    @Test
    fun mediaCard_album_longTitle() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MediaCard(
                    album = AlbumSpecs.longTitle.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }
    }

    @Test
    fun mediaCard_song_vertical() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MediaCard(
                    song = SongSpecs.normal.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }
    }

    @Test
    fun mediaCard_song_horizontal() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MediaCard(
                    song = SongSpecs.normal.value,
                    orientation = Orientation.HORIZONTAL
                )
            }
        }
    }

    @Test
    fun mediaCard_album_vertical_darkMode() {
        paparazzi.snapshot {
            PastelmusicTheme(darkTheme = true) {
                MediaCard(
                    album = AlbumSpecs.normal.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }
    }

    @Test
    fun mediaCard_song_longTitle() {
        paparazzi.snapshot {
            PastelmusicTheme {
                MediaCard(
                    song = SongSpecs.longTitle.value,
                    orientation = Orientation.VERTICAL
                )
            }
        }
    }
}
