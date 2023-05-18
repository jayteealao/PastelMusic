package com.github.jayteealao.pastelmusic.app.data

import android.content.Context
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.datasource.mediastore.ArtistMediaStoreDataSource
import com.github.jayteealao.pastelmusic.app.datasource.mediastore.SongMediaStoreDataSource
import com.github.jayteealao.pastelmusic.app.datasource.util.observeMediaStoreChanges
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.ArtistDetails
import com.github.jayteealao.pastelmusic.app.domain.repository.ArtistRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class ArtistRepositoryImpl @Inject constructor(
    private val artistMediaStore: ArtistMediaStoreDataSource,
    private val songMediaStore: SongMediaStoreDataSource,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : ArtistRepository {
    override fun getAll(): Flow<List<Artist>> {
        return observeMediaStoreChanges {
            artistMediaStore.artists()
        }
    }

    override fun getArtistSongs(artist: Artist): Flow<ArtistDetails> {
        return observeMediaStoreChanges {
            ArtistDetails(
                artist,
                songMediaStore.songs(artist)
            )
        }
    }

    override suspend fun search(searchString: String): MutableList<Artist> {
        return artistMediaStore.search(searchString)
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