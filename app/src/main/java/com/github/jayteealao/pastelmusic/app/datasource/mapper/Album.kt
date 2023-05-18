package com.github.jayteealao.pastelmusic.app.datasource.mapper

import android.annotation.SuppressLint
import android.database.Cursor
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.domain.model.Album

@SuppressLint("Range")
fun Cursor.toAlbumModel(): Album {
//        Log.d("songrepo", getString(getColumnIndex(MediaStore.Audio.Media.ALBUM)))
    return Album(
        getString(getColumnIndex(MediaStore.Audio.Media._ID)),
        getString(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
        getString(getColumnIndex(MediaStore.Audio.Media.ALBUM)),
        getInt(getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)),
        getString(getColumnIndex(MediaStore.Audio.Albums.ARTIST))
    )
}