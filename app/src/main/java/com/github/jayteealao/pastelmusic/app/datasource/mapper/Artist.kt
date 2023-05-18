package com.github.jayteealao.pastelmusic.app.datasource.mapper

import android.annotation.SuppressLint
import android.database.Cursor
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.domain.model.Artist

@SuppressLint("Range")
fun Cursor.toArtistModel(): Artist {
    return Artist(
        getString(getColumnIndex(MediaStore.Audio.Artists._ID)),
        getString(getColumnIndex(MediaStore.Audio.Artists.ARTIST)),
        getInt(getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))
    )
}