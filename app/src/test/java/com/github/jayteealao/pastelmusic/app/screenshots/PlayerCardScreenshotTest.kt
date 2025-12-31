package com.github.jayteealao.pastelmusic.app.screenshots

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.github.jayteealao.pastelmusic.app.components.PlayerCard
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.github.jayteealao.pastelmusic.app.ui.preview.PlayerCardSpecs
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import org.junit.Rule
import org.junit.Test

/**
 * Paparazzi screenshot tests for PlayerCard component.
 * These tests capture JVM-based screenshots for visual regression testing.
 */
class PlayerCardScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        showSystemUi = false
    )

    @Test
    fun playerCard_idle() {
        paparazzi.snapshot {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.idle.value.playbackState,
                    controls = PlayerCardSpecs.idle.value.controls
                )
            }
        }
    }

    @Test
    fun playerCard_playing() {
        paparazzi.snapshot {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.playing.value.playbackState,
                    controls = PlayerCardSpecs.playing.value.controls
                )
            }
        }
    }

    @Test
    fun playerCard_paused() {
        paparazzi.snapshot {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.paused.value.playbackState,
                    controls = PlayerCardSpecs.paused.value.controls
                )
            }
        }
    }

    @Test
    fun playerCard_longTitle() {
        paparazzi.snapshot {
            PastelmusicTheme {
                PlayerCard(
                    playbackState = PlayerCardSpecs.longTitle.value.playbackState,
                    controls = PlayerCardSpecs.longTitle.value.controls
                )
            }
        }
    }

    @Test
    fun playerCard_darkMode() {
        paparazzi.snapshot {
            PastelmusicTheme(darkTheme = true) {
                PlayerCard(
                    playbackState = PlayerCardSpecs.playing.value.playbackState,
                    controls = PlayerCardSpecs.playing.value.controls
                )
            }
        }
    }
}
