package com.github.jayteealao.pastelmusic.app.domain

import com.github.jayteealao.pastelmusic.app.domain.model.formattedDuration
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Unit tests for Song-related utility functions.
 */
class SongTest {

    @Test
    fun `formattedDuration formats zero duration correctly`() {
        val duration = 0L
        assertThat(duration.formattedDuration()).isEqualTo("00:00")
    }

    @Test
    fun `formattedDuration formats seconds only correctly`() {
        val duration = 45000L // 45 seconds
        assertThat(duration.formattedDuration()).isEqualTo("00:45")
    }

    @Test
    fun `formattedDuration formats minutes and seconds correctly`() {
        val duration = 234000L // 3:54
        assertThat(duration.formattedDuration()).isEqualTo("03:54")
    }

    @Test
    fun `formattedDuration formats exact minute correctly`() {
        val duration = 180000L // 3:00
        assertThat(duration.formattedDuration()).isEqualTo("03:00")
    }

    @Test
    fun `formattedDuration formats long duration correctly`() {
        val duration = 600000L // 10:00
        assertThat(duration.formattedDuration()).isEqualTo("10:00")
    }

    @Test
    fun `formattedDuration pads single digit minutes correctly`() {
        val duration = 65000L // 1:05
        assertThat(duration.formattedDuration()).isEqualTo("01:05")
    }

    @Test
    fun `formattedDuration handles very long songs`() {
        val duration = 3661000L // 61:01 (over an hour)
        assertThat(duration.formattedDuration()).isEqualTo("61:01")
    }
}
