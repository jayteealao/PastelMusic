package com.github.jayteealao.pastelmusic.app.data

import android.content.Context
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.datasource.mediastore.SongMediaStoreDataSource
import com.github.jayteealao.pastelmusic.app.datasource.util.observeMediaStoreChanges
import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.domain.repository.SongRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SongRepositoryImpl @Inject constructor(
    private val songMediaStore: SongMediaStoreDataSource,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
) : SongRepository {

    private val scope = CoroutineScope(ioDispatcher + SupervisorJob())

    private val _queue = MutableStateFlow<MutableList<Song>>(mutableListOf())
//    val queue: StateFlow<List<Song>>
//        get() = _queue

    private var _currentIndex = MutableStateFlow(-1)
    val currentIndex: StateFlow<Int>
        get() = _currentIndex

    override fun getAll(): Flow<List<Song>> {

        return observeMediaStoreChanges{
            songMediaStore.all()
        }

    }

    override suspend fun getSongs(album: Album): Flow<MutableList<Song>> {
        return observeMediaStoreChanges {
            songMediaStore.songs(album)
        }
    }

    override suspend fun getSongs(artist: Artist): Flow<MutableList<Song>> {
        return observeMediaStoreChanges {
            songMediaStore.songs(artist)
        }
    }

    fun next(): Song? {
        return if (_queue.value.size - 1 > _currentIndex.value) {
            _currentIndex.value += 1
            _queue.value[_currentIndex.value]
        } else {
            null
        }
    }

    fun previous(): Song? {
        return if (_currentIndex.value >= 1) {
            _currentIndex.value -= 1
            _queue.value[_currentIndex.value]
        } else {
            null
        }
    }

    fun addAtStart(song: Song) = scope.launch {

        val songs = _queue.value.toMutableList()
        val songExpectedIndex = songs.indexOf(song)
        _currentIndex.value = if (songExpectedIndex != -1) {
            songExpectedIndex
        } else {
            songs.add(0, song)
            scope.launch {
                _queue.value = songs
            }
            0
        }
    }

    override fun getPlayingQueue(): StateFlow<List<Song>> = _queue

    override fun getPlayingQueueIndex(): StateFlow<Int> = currentIndex

    override suspend fun addToQueue(songs: List<Song>) {
        scope.launch {
            val currentSongs = _queue.value.toMutableList()
            currentSongs.addAll(songs)
            scope.launch {
                _queue.value = currentSongs
            }
        }
    }

    override suspend fun addToQueue(song: Song) {
        scope.launch {
            val currentSongs = _queue.value.toMutableList()
            currentSongs.add(song)
            scope.launch {
                _queue.value = currentSongs
            }
        }
    }

    override fun clearQueue() {
        scope.launch {
            _queue.value = mutableListOf()
        }
    }

    override suspend fun setPlayingQueue(songs: List<Song>) {
        scope.launch {
            _queue.value = songs.toMutableList()
        }
    }

    override suspend fun setPlayingQueueIndex(index: Int) {
        _currentIndex.value = index
    }

    override suspend fun search(searchString: String): MutableList<Song> {
        return songMediaStore.search(searchString)
    }

    override suspend fun searchById(mediaId: String): MutableList<Song> {
        return songMediaStore.searchById(mediaId)
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