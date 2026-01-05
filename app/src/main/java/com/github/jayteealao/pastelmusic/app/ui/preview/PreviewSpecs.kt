package com.github.jayteealao.pastelmusic.app.ui.preview

import android.net.Uri
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.EMPTY_SONG
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.mediaservice.util.PlayerControl

/**
 * Base interface for preview specifications.
 * Used to provide named data instances for both previews and screenshot tests.
 */
interface PreviewSpec<T> {
    val name: String
    val value: T
}

/**
 * No-op PlayerControl for use in previews and tests.
 */
val PreviewPlayerControl = object : PlayerControl {
    override fun play() = Unit
    override fun pause() = Unit
    override fun skipNext() = Unit
    override fun skipPrevious() = Unit
}

/**
 * Factory for creating preview/test data.
 */
object PreviewData {

    fun createSong(
        id: String = "1",
        title: String = "Test Song",
        artist: String = "Test Artist",
        album: String = "Test Album",
        duration: Long = 180000L,
        albumId: String = "1",
        artistId: String = "1"
    ): Song = Song(
        title = title,
        artist = artist,
        album = album,
        id = id,
        displayName = "$title.mp3",
        uri = Uri.EMPTY,
        duration = duration,
        albumArt = Uri.EMPTY,
        albumId = albumId,
        artistId = artistId
    )

    fun createAlbum(
        id: String = "1",
        albumId: String = "1",
        title: String = "Test Album",
        count: Int = 10,
        artist: String = "Test Artist"
    ): Album = Album(
        id = id,
        albumId = albumId,
        title = title,
        count = count,
        artist = artist
    )

    fun createPlaybackState(
        isPlaying: Boolean = false,
        song: Song = createSong()
    ): PlaybackState = PlaybackState(
        playbackState = if (isPlaying) 3 else 1,
        playWhenReady = isPlaying,
        duration = song.duration,
        song = song
    )
}

// ============================================================================
// PlaybackState Preview Specs
// ============================================================================

data class PlaybackStateSpec(
    override val name: String,
    override val value: PlaybackState
) : PreviewSpec<PlaybackState>

object PlaybackStateSpecs {
    val idle = PlaybackStateSpec(
        name = "Idle",
        value = PlaybackState()
    )

    val playing = PlaybackStateSpec(
        name = "Playing",
        value = PreviewData.createPlaybackState(
            isPlaying = true,
            song = PreviewData.createSong(
                title = "Going Home",
                artist = "Johnny Drille",
                album = "Before We Fall Asleep",
                duration = 234000
            )
        )
    )

    val paused = PlaybackStateSpec(
        name = "Paused",
        value = PreviewData.createPlaybackState(
            isPlaying = false,
            song = PreviewData.createSong(
                title = "Essence",
                artist = "Wizkid",
                album = "Made In Lagos",
                duration = 256000
            )
        )
    )

    val longTitle = PlaybackStateSpec(
        name = "LongTitle",
        value = PreviewData.createPlaybackState(
            isPlaying = true,
            song = PreviewData.createSong(
                title = "This Is A Very Long Song Title That Should Overflow The Text Container",
                artist = "Artist With An Exceptionally Long Name",
                album = "Album With A Very Long Name As Well"
            )
        )
    )

    val all = listOf(idle, playing, paused, longTitle)
}

class PlaybackStatePreviewProvider : PreviewParameterProvider<PlaybackState> {
    override val values: Sequence<PlaybackState> = PlaybackStateSpecs.all.asSequence().map { it.value }
}

// ============================================================================
// Album Preview Specs
// ============================================================================

data class AlbumSpec(
    override val name: String,
    override val value: Album
) : PreviewSpec<Album>

object AlbumSpecs {
    val normal = AlbumSpec(
        name = "Normal",
        value = PreviewData.createAlbum(
            title = "Before We Fall Asleep",
            artist = "Johnny Drille",
            count = 12
        )
    )

    val longTitle = AlbumSpec(
        name = "LongTitle",
        value = PreviewData.createAlbum(
            id = "2",
            title = "This Is A Very Long Album Title That Should Overflow",
            artist = "Artist With A Really Long Name",
            count = 25
        )
    )

    val singleSong = AlbumSpec(
        name = "SingleSong",
        value = PreviewData.createAlbum(
            id = "3",
            title = "Single",
            artist = "Rema",
            count = 1
        )
    )

    val all = listOf(normal, longTitle, singleSong)
}

class AlbumPreviewProvider : PreviewParameterProvider<Album> {
    override val values: Sequence<Album> = AlbumSpecs.all.asSequence().map { it.value }
}

// ============================================================================
// Song Preview Specs
// ============================================================================

data class SongSpec(
    override val name: String,
    override val value: Song
) : PreviewSpec<Song>

object SongSpecs {
    val normal = SongSpec(
        name = "Normal",
        value = PreviewData.createSong(
            title = "Calm Down",
            artist = "Rema",
            album = "Rave & Roses",
            duration = 214000
        )
    )

    val longTitle = SongSpec(
        name = "LongTitle",
        value = PreviewData.createSong(
            id = "2",
            title = "This Is A Very Long Song Title That Will Definitely Cause Text Overflow",
            artist = "Artist With A Really Long Stage Name",
            album = "Very Long Album Title Here"
        )
    )

    val shortDuration = SongSpec(
        name = "ShortDuration",
        value = PreviewData.createSong(
            id = "3",
            title = "Interlude",
            artist = "Various Artists",
            album = "Compilation",
            duration = 30000 // 30 seconds
        )
    )

    val longDuration = SongSpec(
        name = "LongDuration",
        value = PreviewData.createSong(
            id = "4",
            title = "Extended Mix",
            artist = "DJ Master",
            album = "Club Hits",
            duration = 600000 // 10 minutes
        )
    )

    val all = listOf(normal, longTitle, shortDuration, longDuration)
}

class SongPreviewProvider : PreviewParameterProvider<Song> {
    override val values: Sequence<Song> = SongSpecs.all.asSequence().map { it.value }
}

// ============================================================================
// PlayerCard Preview Specs (combines PlaybackState + Controls)
// ============================================================================

data class PlayerCardState(
    val playbackState: PlaybackState,
    val controls: PlayerControl = PreviewPlayerControl
)

data class PlayerCardSpec(
    override val name: String,
    override val value: PlayerCardState
) : PreviewSpec<PlayerCardState>

object PlayerCardSpecs {
    val idle = PlayerCardSpec(
        name = "Idle",
        value = PlayerCardState(PlaybackStateSpecs.idle.value)
    )

    val playing = PlayerCardSpec(
        name = "Playing",
        value = PlayerCardState(PlaybackStateSpecs.playing.value)
    )

    val paused = PlayerCardSpec(
        name = "Paused",
        value = PlayerCardState(PlaybackStateSpecs.paused.value)
    )

    val longTitle = PlayerCardSpec(
        name = "LongTitle",
        value = PlayerCardState(PlaybackStateSpecs.longTitle.value)
    )

    val all = listOf(idle, playing, paused, longTitle)
}

class PlayerCardPreviewProvider : PreviewParameterProvider<PlayerCardState> {
    override val values: Sequence<PlayerCardState> = PlayerCardSpecs.all.asSequence().map { it.value }
}
