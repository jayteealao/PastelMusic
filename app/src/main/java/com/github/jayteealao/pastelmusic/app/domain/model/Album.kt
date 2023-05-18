package com.github.jayteealao.pastelmusic.app.domain.model

import android.content.ContentUris
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_MEDIA_ID

data class Album(
    val id: String,
    val albumId: String,
    val title: String,
    val count: Int,
    val artist: String
    ) {

    val albumArtPath: Uri by lazy {
        if (id == DEFAULT_MEDIA_ID) {
            Uri.EMPTY
        } else {
            ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), id.toLong())
        }
    }
    val uri: Uri by lazy {
        ContentUris.withAppendedId(Uri.parse("content://media/external/audio"), id.toLong())
    }
    val mediaItem: MediaItem by lazy {
        if (id == DEFAULT_MEDIA_ID) {
            MediaItem.EMPTY
        } else {
            MediaItem.Builder().apply {
                setMediaId(id)
                setMediaMetadata(
                    MediaMetadata.Builder().apply {
                        setTitle(title)
                        setArtist(artist)
                        setArtworkUri(albumArtPath)
                        setIsPlayable(false)
                        setFolderType(MediaMetadata.FOLDER_TYPE_ALBUMS)
                    }.build()
                )
            }.build()
        }
    }
}

val EMPTY_ALBUM = Album(
    DEFAULT_MEDIA_ID,
    DEFAULT_MEDIA_ID,
    "",
    0,
    "",
)