package com.github.jayteealao.pastelmusic.app.datasource.mediastore

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.datasource.mapper.toAlbumModel
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AlbumMediaStoreDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    companion object {
        const val sort = MediaStore.Audio.Albums.ALBUM + " ASC"
        const val selection = MediaStore.Audio.Albums.ALBUM + " LIKE ?"
        val selectionArgs = arrayOf("%title%")


        @SuppressLint("InlinedApi")
        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ARTIST,
        )
    }

    private fun albumsCursor(selection: String? = null, selectionArgs: Array<String>? = null): Cursor? {

        return context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sort
        )
    }

    suspend fun albums() = withContext(ioDispatcher) {
        val albums = mutableListOf<Album>()
        val c = albumsCursor()
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = c.toAlbumModel()
                albums.add(item)
                this.moveToNext()
            }
            c.close()
        }
        albums
    }

    suspend fun search(searchString: String) = withContext(ioDispatcher) {
        val albums = mutableListOf<Album>()
        val c = albumsCursor(
            selection,
            selectionArgs
        )
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toAlbumModel()
                albums.add(item)
                this.moveToNext()
            }
            c.close()
        }
        albums
    }
}