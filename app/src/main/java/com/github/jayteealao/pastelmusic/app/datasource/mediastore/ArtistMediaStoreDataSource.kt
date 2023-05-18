package com.github.jayteealao.pastelmusic.app.datasource.mediastore

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.datasource.mapper.toArtistModel
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ArtistMediaStoreDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    companion object {
        const val sort = MediaStore.Audio.Artists.ARTIST + " ASC"
        const val selection = MediaStore.Audio.Artists.ARTIST + " LIKE ?"
        val selectionArgs = arrayOf("%title%")

        val projection = arrayOf(MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS)
    }

    private fun artistCursor(selection: String? = null, selectionArgs: Array<String>? = null): Cursor? {

        return context.contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sort)

    }

    suspend fun artists() = withContext(ioDispatcher) {
        val artists = mutableListOf<Artist>()
        val c = artistCursor()
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = c.toArtistModel()
                artists.add(item)
                this.moveToNext()
            }
            c.close()
        }
        artists
    }

    suspend fun search(searchString: String) = withContext(ioDispatcher) {
        val artists = mutableListOf<Artist>()
        val c = artistCursor(selection, selectionArgs)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toArtistModel()
                artists.add(item)
                this.moveToNext()
            }
            c.close()
        }
        artists
    }
}