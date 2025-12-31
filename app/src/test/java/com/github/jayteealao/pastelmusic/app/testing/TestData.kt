package com.github.jayteealao.pastelmusic.app.testing

import android.net.Uri
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.mediaservice.PlaybackState
import com.github.jayteealao.pastelmusic.app.mediaservice.util.PlayerControl

/**
 * Test data factory for creating consistent test objects.
 * This serves as a single source of truth for preview and test data.
 */
object TestData {

    /**
     * Creates a test Song with customizable properties.
     */
    fun createSong(
        id: String = "1",
        title: String = "Test Song",
        artist: String = "Test Artist",
        album: String = "Test Album",
        duration: Long = 180000L, // 3 minutes
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

    /**
     * Creates a test Album with customizable properties.
     */
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

    /**
     * Creates a test Artist with customizable properties.
     */
    fun createArtist(
        id: String = "1",
        name: String = "Test Artist",
        count: Int = 5
    ): Artist = Artist(
        id = id,
        name = name,
        count = count
    )

    /**
     * Creates a test PlaybackState with customizable properties.
     */
    fun createPlaybackState(
        isPlaying: Boolean = false,
        song: Song = createSong()
    ): PlaybackState = PlaybackState(
        playbackState = if (isPlaying) 3 else 1, // STATE_READY or STATE_IDLE
        playWhenReady = isPlaying,
        duration = song.duration,
        song = song
    )

    /**
     * Sample songs for testing.
     */
    val sampleSongs = listOf(
        createSong(id = "1", title = "Going Home", artist = "Johnny Drille", album = "Before We Fall Asleep", duration = 234000, albumId = "1", artistId = "1"),
        createSong(id = "2", title = "Shine Your Light", artist = "Burna Boy", album = "African Giant", duration = 198000, albumId = "2", artistId = "2"),
        createSong(id = "3", title = "Essence", artist = "Wizkid", album = "Made In Lagos", duration = 256000, albumId = "3", artistId = "3"),
        createSong(id = "4", title = "Love Nwantiti", artist = "CKay", album = "Boyfriend", duration = 180000, albumId = "4", artistId = "4"),
        createSong(id = "5", title = "Calm Down", artist = "Rema", album = "Rave & Roses", duration = 214000, albumId = "5", artistId = "5")
    )

    /**
     * Sample albums for testing.
     */
    val sampleAlbums = listOf(
        createAlbum(id = "1", title = "Before We Fall Asleep", artist = "Johnny Drille", count = 12),
        createAlbum(id = "2", title = "African Giant", artist = "Burna Boy", count = 15),
        createAlbum(id = "3", title = "Made In Lagos", artist = "Wizkid", count = 14),
        createAlbum(id = "4", title = "Boyfriend", artist = "CKay", count = 8),
        createAlbum(id = "5", title = "Rave & Roses", artist = "Rema", count = 16)
    )

    /**
     * Sample artists for testing.
     */
    val sampleArtists = listOf(
        createArtist(id = "1", name = "Johnny Drille", count = 12),
        createArtist(id = "2", name = "Burna Boy", count = 25),
        createArtist(id = "3", name = "Wizkid", count = 18),
        createArtist(id = "4", name = "CKay", count = 10),
        createArtist(id = "5", name = "Rema", count = 20)
    )

    /**
     * A no-op PlayerControl for testing and previews.
     */
    val noOpPlayerControl = object : PlayerControl {
        override fun play() = Unit
        override fun pause() = Unit
        override fun skipNext() = Unit
        override fun skipPrevious() = Unit
    }

    /**
     * Edge case: Very long song title for testing text overflow.
     */
    val longTitleSong = createSong(
        id = "long",
        title = "This Is A Very Long Song Title That Should Definitely Cause Text Overflow In The UI",
        artist = "Artist With A Really Long Name That Also Overflows"
    )

    /**
     * Edge case: Empty song for testing empty states.
     */
    val emptySong = createSong(
        id = "empty",
        title = "",
        artist = "",
        album = "",
        duration = 0L
    )
}
