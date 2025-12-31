package com.github.jayteealao.pastelmusic.app.robolectric

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.github.jayteealao.pastelmusic.app.R
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Robolectric tests for theme and resources.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class ThemeResourcesTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    fun `app name string resource exists`() {
        val appName = context.getString(R.string.app_name)
        assertThat(appName).isNotEmpty()
        assertThat(appName).isEqualTo("PastelMusic")
    }

    @Test
    fun `PastelmusicTheme applies correctly`() {
        var primaryColorApplied = false

        composeTestRule.setContent {
            PastelmusicTheme {
                primaryColorApplied = MaterialTheme.colorScheme.primary != null
            }
        }

        assertThat(primaryColorApplied).isTrue()
    }

    @Test
    fun `PastelmusicTheme dark mode applies different colors`() {
        var darkPrimaryColor: Long = 0
        var lightPrimaryColor: Long = 0

        composeTestRule.setContent {
            PastelmusicTheme(darkTheme = true) {
                darkPrimaryColor = MaterialTheme.colorScheme.primary.value.toLong()
            }
        }

        composeTestRule.setContent {
            PastelmusicTheme(darkTheme = false) {
                lightPrimaryColor = MaterialTheme.colorScheme.primary.value.toLong()
            }
        }

        // Dark and light themes should have different primary colors
        assertThat(darkPrimaryColor).isNotEqualTo(lightPrimaryColor)
    }

    @Test
    fun `theme typography is applied`() {
        var hasTypography = false

        composeTestRule.setContent {
            PastelmusicTheme {
                hasTypography = MaterialTheme.typography.bodyLarge != null
            }
        }

        assertThat(hasTypography).isTrue()
    }

    @Test
    fun `theme shapes are applied`() {
        var hasShapes = false

        composeTestRule.setContent {
            PastelmusicTheme {
                hasShapes = MaterialTheme.shapes.medium != null
            }
        }

        assertThat(hasShapes).isTrue()
    }
}
