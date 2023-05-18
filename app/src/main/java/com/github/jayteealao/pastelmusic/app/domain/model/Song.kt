package com.github.jayteealao.pastelmusic.app.domain.model

import android.content.ContentUris
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_MEDIA_ID

data class Song(
    val title: String,
    val artist: String,
    val album: String,
    val id: String,
    val displayName: String,
    val uri: Uri,
    val duration: Long,
    val albumArt: Uri,
    val albumId: String,
    val artistId: String
    ) {
    val albumArtPath: Uri by lazy {
//        Log.d("songart", "${ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId.toLong())}")
        if (id == DEFAULT_MEDIA_ID) {
            Uri.EMPTY
        } else {
            ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumId.toLong())
        }
    }

    val mediaItem: MediaItem by lazy {
        if (uri == Uri.EMPTY && title == DEFAULT_MEDIA_ID) {
            MediaItem.EMPTY
        } else {
            MediaItem.Builder().apply {
                setMediaId(id)
                setRequestMetadata(
                    MediaItem.RequestMetadata.Builder()
                        .setMediaUri(uri)
                        .build()
                )
                setMediaMetadata(
                    MediaMetadata.Builder().apply {
                        setTitle(title)
                        setArtist(artist)
                        setAlbumTitle(album)
                        setDisplayTitle(title)
                        setArtworkUri(albumArtPath)
                        setIsPlayable(true)
                        setFolderType(MediaMetadata.FOLDER_TYPE_NONE)
                        setExtras(
                            bundleOf(
                                ARTIST_ID to artistId,
                                ALBUM_ID to albumId,
                                ALBUM_ART_PATH to albumArtPath,
                                DURATION to duration
                            )
                        )
                    }.build()
                )
            }.build()
        }
    }
}

val EMPTY_SONG = Song(
    "",
    "",
    "",
    DEFAULT_MEDIA_ID,
    "",
    Uri.EMPTY,
    C.TIME_UNSET,
    Uri.EMPTY,
    DEFAULT_MEDIA_ID,
    DEFAULT_MEDIA_ID,
)

fun Long.formattedDuration(): String {
    val sec = this / 1000
    return (sec / 60).toString().padStart(2, '0') + ":" + (sec % 60).toString().padStart(2, '0')
}

internal const val ARTIST_ID = "artist_id"
internal const val ALBUM_ID = "album_id"
internal const val ALBUM_ART_PATH = "album_art_path"
internal const val DURATION = "duration"
