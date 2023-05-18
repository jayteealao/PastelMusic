package com.github.jayteealao.pastelmusic.app.domain.repository

import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SongRepository  {
    fun getAll(): Flow<List<Song>>
    suspend fun getSongs(album: Album): Flow<MutableList<Song>>
    suspend fun getSongs(artist: Artist): Flow<MutableList<Song>>
    fun getPlayingQueue(): StateFlow<List<Song>>
    fun getPlayingQueueIndex(): StateFlow<Int>
    suspend fun addToQueue(songs: List<Song>)
    suspend fun addToQueue(song: Song)
    fun clearQueue()
    suspend fun setPlayingQueue(songs: List<Song>)
    suspend fun setPlayingQueueIndex(index: Int)
    suspend fun search(searchString: String): MutableList<Song>
    suspend fun searchById(mediaId: String): MutableList<Song>
}