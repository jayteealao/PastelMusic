package com.github.jayteealao.pastelmusic.app.ui.preview

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.github.jayteealao.pastelmusic.app.components.PlayerCard
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelmusicTheme

/**
 * Compose previews for PlayerCard component.
 * Uses PreviewParameterProvider for state enumeration.
 */

@Preview(
    name = "PlayerCard - Light",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun PlayerCardPreview(
    @PreviewParameter(PlayerCardPreviewProvider::class) state: PlayerCardState
) {
    PastelmusicTheme(darkTheme = false) {
        PlayerCard(
            playbackState = state.playbackState,
            controls = state.controls
        )
    }
}

@Preview(
    name = "PlayerCard - Dark",
    showBackground = true,
    backgroundColor = 0xFF121212,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PlayerCardDarkPreview(
    @PreviewParameter(PlayerCardPreviewProvider::class) state: PlayerCardState
) {
    PastelmusicTheme(darkTheme = true) {
        PlayerCard(
            playbackState = state.playbackState,
            controls = state.controls
        )
    }
}

@Preview(
    name = "PlayerCard - Idle",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun PlayerCardIdlePreview() {
    PastelmusicTheme {
        PlayerCard(
            playbackState = PlayerCardSpecs.idle.value.playbackState,
            controls = PreviewPlayerControl
        )
    }
}

@Preview(
    name = "PlayerCard - Playing",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun PlayerCardPlayingPreview() {
    PastelmusicTheme {
        PlayerCard(
            playbackState = PlayerCardSpecs.playing.value.playbackState,
            controls = PreviewPlayerControl
        )
    }
}

@Preview(
    name = "PlayerCard - Long Title",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun PlayerCardLongTitlePreview() {
    PastelmusicTheme {
        PlayerCard(
            playbackState = PlayerCardSpecs.longTitle.value.playbackState,
            controls = PreviewPlayerControl
        )
    }
}
