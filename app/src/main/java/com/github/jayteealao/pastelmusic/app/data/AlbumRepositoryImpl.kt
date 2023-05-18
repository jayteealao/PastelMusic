package com.github.jayteealao.pastelmusic.app.data

import android.content.Context
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.datasource.mediastore.AlbumMediaStoreDataSource
import com.github.jayteealao.pastelmusic.app.datasource.mediastore.SongMediaStoreDataSource
import com.github.jayteealao.pastelmusic.app.datasource.util.observeMediaStoreChanges
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.AlbumDetails
import com.github.jayteealao.pastelmusic.app.domain.repository.AlbumRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class AlbumRepositoryImpl @Inject constructor(
    private val albumMediaStoreDataSource: AlbumMediaStoreDataSource,
    private val songMediaStoreDataSource: SongMediaStoreDataSource,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : AlbumRepository {
    override fun getAll(): Flow<List<Album>> {
        return observeMediaStoreChanges { albumMediaStoreDataSource.albums() }
    }

    override fun getAlbumSongs(album: Album): Flow<AlbumDetails> {
        return observeMediaStoreChanges {
            AlbumDetails(
                album,
                songMediaStoreDataSource.songs(album)
            )
        }
    }

    override suspend fun search(searchString: String): MutableList<Album> {
        return albumMediaStoreDataSource.search(searchString)
    }

    private fun <T> observeMediaStoreChanges(getData: suspend () -> T): Flow<T> {
        return observeMediaStoreChanges(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            context.contentResolver,
        ) {
            getData()
        }
    }
}