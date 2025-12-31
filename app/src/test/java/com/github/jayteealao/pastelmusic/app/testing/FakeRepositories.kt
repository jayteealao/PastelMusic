package com.github.jayteealao.pastelmusic.app.testing

import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.AlbumDetails
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.ArtistDetails
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.domain.repository.AlbumRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.ArtistRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.SongRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf

/**
 * Fake implementation of SongRepository for testing purposes.
 */
class FakeSongRepository : SongRepository {
    private val songs = MutableStateFlow<List<Song>>(emptyList())
    private val playingQueue = MutableStateFlow<List<Song>>(emptyList())
    private val playingQueueIndex = MutableStateFlow(0)

    fun setSongs(songList: List<Song>) {
        songs.value = songList
    }

    override fun getAll(): Flow<List<Song>> = songs

    override suspend fun getSongs(album: Album): Flow<MutableList<Song>> {
        return flowOf(songs.value.filter { it.albumId == album.id }.toMutableList())
    }

    override suspend fun getSongs(artist: Artist): Flow<MutableList<Song>> {
        return flowOf(songs.value.filter { it.artistId == artist.id }.toMutableList())
    }

    override fun getPlayingQueue(): StateFlow<List<Song>> = playingQueue

    override fun getPlayingQueueIndex(): StateFlow<Int> = playingQueueIndex

    override suspend fun addToQueue(songs: List<Song>) {
        playingQueue.value = playingQueue.value + songs
    }

    override suspend fun addToQueue(song: Song) {
        playingQueue.value = playingQueue.value + song
    }

    override fun clearQueue() {
        playingQueue.value = emptyList()
    }

    override suspend fun setPlayingQueue(songs: List<Song>) {
        playingQueue.value = songs
    }

    override suspend fun setPlayingQueueIndex(index: Int) {
        playingQueueIndex.value = index
    }

    override suspend fun search(searchString: String): MutableList<Song> {
        return songs.value.filter {
            it.title.contains(searchString, ignoreCase = true) ||
            it.artist.contains(searchString, ignoreCase = true)
        }.toMutableList()
    }

    override suspend fun searchById(mediaId: String): MutableList<Song> {
        return songs.value.filter { it.id == mediaId }.toMutableList()
    }
}

/**
 * Fake implementation of AlbumRepository for testing purposes.
 */
class FakeAlbumRepository : AlbumRepository {
    private val albums = MutableStateFlow<List<Album>>(emptyList())
    private val albumSongsMap = mutableMapOf<String, List<Song>>()

    fun setAlbums(albumList: List<Album>) {
        albums.value = albumList
    }

    fun setAlbumSongs(albumId: String, songs: List<Song>) {
        albumSongsMap[albumId] = songs
    }

    override fun getAll(): Flow<List<Album>> = albums

    override fun getAlbumSongs(album: Album): Flow<AlbumDetails> {
        val songs = albumSongsMap[album.id] ?: emptyList()
        return flowOf(AlbumDetails(album = album, songs = songs))
    }

    override suspend fun search(searchString: String): MutableList<Album> {
        return albums.value.filter {
            it.title.contains(searchString, ignoreCase = true) ||
            it.artist.contains(searchString, ignoreCase = true)
        }.toMutableList()
    }
}

/**
 * Fake implementation of ArtistRepository for testing purposes.
 */
class FakeArtistRepository : ArtistRepository {
    private val artists = MutableStateFlow<List<Artist>>(emptyList())
    private val artistSongsMap = mutableMapOf<String, List<Song>>()

    fun setArtists(artistList: List<Artist>) {
        artists.value = artistList
    }

    fun setArtistSongs(artistId: String, songs: List<Song>) {
        artistSongsMap[artistId] = songs
    }

    override fun getAll(): Flow<List<Artist>> = artists

    override fun getArtistSongs(artist: Artist): Flow<ArtistDetails> {
        val songs = artistSongsMap[artist.id] ?: emptyList()
        return flowOf(ArtistDetails(artist = artist, songs = songs))
    }

    override suspend fun search(searchString: String): MutableList<Artist> {
        return artists.value.filter {
            it.name.contains(searchString, ignoreCase = true)
        }.toMutableList()
    }
}
