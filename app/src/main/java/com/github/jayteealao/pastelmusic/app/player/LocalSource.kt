package com.github.jayteealao.pastelmusic.app.player

import android.os.Bundle
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import com.github.jayteealao.pastelmusic.app.database.SongsRepository
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Singleton
internal class LocalSource @Inject constructor(private val songsRepository: SongsRepository): AbstractMusicSource() {

//    @Inject
//    lateinit var songsRepository: SongsRepository

    companion object {
        const val ORIGINAL_ARTWORK_URI_KEY = "com.github.jayteealao.JSON_ARTWORK_URI"
        const val ALBUM_ID = "com.github.jayteealao.ALBUM_ID"
    }

    private var catalog: List<MediaItem> = emptyList()

    init {
        state = STATE_INITIALIZING
    }

    override suspend fun load() {
        updateCatalog().let {
            catalog = it
            state = STATE_INITIALIZED
        }
        Log.d("LocalSource", "${catalog.map { it.mediaMetadata.displayTitle }}")
    }

    override fun iterator(): Iterator<MediaItem> = catalog.iterator()

    private suspend fun updateCatalog(): List<MediaItem> {
        return withContext(Dispatchers.IO) {
            songsRepository.all().map {
                Log.d("songuri", "${it}")

                val mediaMetadata = MediaMetadata.Builder()
                    .from(it)
                    .apply {
                        val extras = Bundle()
                        extras.putString(ORIGINAL_ARTWORK_URI_KEY, it.albumArtPath.toString())
                        extras.putString(ALBUM_ID, it.albumId)
                        extras.putString("id", it.id)
                        setExtras(extras)
                    }.build()

                MediaItem.Builder()
                    .apply {
                        setMediaId(it.id)
                        setUri(it.uri)
                        setMimeType(MimeTypes.BASE_TYPE_AUDIO)
                        setMediaMetadata(mediaMetadata)
                    }.build()
            }.toList()
        }
    }
}

/**
 * Extension method for [MediaMetadataCompat.Builder] to set the fields from
 * our JSON constructed object (to make the code a bit easier to see).
 */
fun MediaMetadata.Builder.from(song: Song): MediaMetadata.Builder {
    setTitle(song.title)
    setDisplayTitle(song.title)
    setArtist(song.artist)
    setAlbumTitle(song.album)
//    setGenre(jsonMusic.genre)
//    setArtworkUri(Uri.parse(jsonMusic.image))
    setArtworkUri(song.albumArtPath)
//    setTrackNumber(jsonMusic.trackNumber.toInt())
//    setTotalTrackCount(jsonMusic.totalTrackCount.toInt())
    setFolderType(MediaMetadata.FOLDER_TYPE_NONE)
    setIsPlayable(true)
    // The duration from the JSON is given in seconds, but the rest of the code works in
    // milliseconds. Here's where we convert to the proper units.
//    val durationMs = TimeUnit.SECONDS.toMillis(jsonMusic.duration)
    val durationMs = song.duration
    val bundle = Bundle()
    bundle.putLong("durationMs", durationMs)
    return this
}

fun MediaMetadata.Builder.from(album: Album): MediaMetadata.Builder {
    setTitle(album.title)
    setDisplayTitle(album.title)
    setArtist(album.artist)
    setAlbumTitle(album.title)
//    setGenre(jsonMusic.genre)
//    setArtworkUri(Uri.parse(jsonMusic.image))
    setArtworkUri(album.albumArtPath)
//    setTrackNumber(jsonMusic.trackNumber.toInt())
//    setTotalTrackCount(jsonMusic.totalTrackCount.toInt())
    setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
    setIsPlayable(false)
    return this
}
