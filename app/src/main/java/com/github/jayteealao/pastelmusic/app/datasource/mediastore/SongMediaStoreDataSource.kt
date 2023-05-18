package com.github.jayteealao.pastelmusic.app.datasource.mediastore

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.datasource.mapper.toSongModel
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

class SongMediaStoreDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    private val scope = CoroutineScope(ioDispatcher + SupervisorJob())

    private fun songsCursor(selection: String? = null, selectionArgs: Array<String>? = null): Cursor? {

        return context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
            selection, selectionArgs, sort
        )
    }

    suspend fun all() = withContext(ioDispatcher) {
        val songInfo = mutableListOf<Song>()
        val c = songsCursor(selection)
        c?.apply {
            this.moveToFirst()
//            Log.d("Column-Names", "${this.columnNames.asList()}")
            while (!this.isAfterLast) {
                val item = this.toSongModel()
                songInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songInfo
    }

    suspend fun songs(album: Album) = withContext(ioDispatcher) {
        val songsInfo = mutableListOf<Song>()
//        val query = MediaStore.Audio.Media.ALBUM_ID + " = '" + album.id + "'"
        val query = "$selection AND ${MediaStore.Audio.Media.ALBUM_ID}  = '${album.id}'"
        val c = songsCursor(query)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSongModel()
                songsInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songsInfo
    }

    suspend fun songs(artist: Artist) = withContext(ioDispatcher) {
        val songsInfo = mutableListOf<Song>()
        val query = "$selection AND ${MediaStore.Audio.Media.ARTIST_ID}  = '${artist.id}'"
        val c = songsCursor(query)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSongModel()
                songsInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songsInfo
    }

    suspend fun search(searchString: String) = withContext(ioDispatcher) {
        val songs = mutableListOf<Song>()
        val query = "$selection AND LOWER(${MediaStore.Audio.Media.TITLE} ) LIKE LOWER('$searchString%') "
        val c = songsCursor(query)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSongModel()
                songs.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songs
    }

    suspend fun searchById(mediaId: String) = withContext(ioDispatcher) {
        val songs = mutableListOf<Song>()
        val query = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media._ID} = ?"
        val selectionArgs = arrayOf(mediaId)
        val c = songsCursor(query, selectionArgs)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSongModel()
                songs.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songs
    }

    companion object {
        const val sort = "LOWER(${MediaStore.Audio.Media.TITLE})"
        const val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND LOWER(${MediaStore.Audio.Media.DISPLAY_NAME})  NOT LIKE  LOWER('%.wma') "

        @SuppressLint("InlinedApi")
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID
        )
    }
}