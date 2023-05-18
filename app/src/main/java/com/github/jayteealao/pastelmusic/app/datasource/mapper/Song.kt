package com.github.jayteealao.pastelmusic.app.datasource.mapper

import android.annotation.SuppressLint
import android.content.ContentUris
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.media3.common.MediaItem
import com.github.jayteealao.pastelmusic.app.domain.model.ALBUM_ID
import com.github.jayteealao.pastelmusic.app.domain.model.ARTIST_ID
import com.github.jayteealao.pastelmusic.app.domain.model.DURATION
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.extensions.asArtworkUri
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_ALBUM_ID
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_ARTIST_ID
import com.github.jayteealao.pastelmusic.app.mediaservice.util.Constants.DEFAULT_DURATION_MS

@SuppressLint("InlinedApi", "Range")
fun Cursor.toSongModel(): Song {
//        Log.d("songrepo", getString(getColumnIndex(MediaStore.Audio.Media.TITLE)))
//        Log.d("songrepo2", getString(getColumnIndex(MediaStore.Images.Media.DATA)).toString())
    return Song(
        getString(getColumnIndex(MediaStore.Audio.Media.TITLE)),
        getString(getColumnIndex(MediaStore.Audio.Media.ARTIST)),
        getString(getColumnIndex(MediaStore.Audio.Media.ALBUM)),
        getString(getColumnIndex(MediaStore.Audio.Media._ID)),
        getString(getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
        ContentUris.withAppendedId(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, getLong(getColumnIndex(
                MediaStore.Audio.Media._ID))),
        getLong(getColumnIndex(MediaStore.Audio.Media.DURATION)),
        getLong(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).asArtworkUri(),
        getString(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
        getString(getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
    )
}

fun MediaItem.toSongModel(): Song {
    return Song(
        title = mediaMetadata.title.toString(),
        artist = mediaMetadata.artist.toString(),
        album = mediaMetadata.albumTitle.toString(),
        id = mediaId,
        displayName = mediaMetadata.displayTitle.toString(),
        uri = requestMetadata.mediaUri ?: Uri.EMPTY,
        duration = mediaMetadata.extras?.getLong(DURATION) ?: DEFAULT_DURATION_MS,
        albumArt = mediaMetadata.artworkUri ?: Uri.EMPTY,
        albumId = (mediaMetadata.extras?.getString(ALBUM_ID) ?: DEFAULT_ALBUM_ID).toString(),
        artistId = (mediaMetadata.extras?.getString(ARTIST_ID) ?: DEFAULT_ARTIST_ID).toString()
    )
}