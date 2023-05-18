package com.github.jayteealao.pastelmusic.app.domain.repository

import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.AlbumDetails
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {
    fun getAll(): Flow<List<Album>>
    fun getAlbumSongs(album: Album): Flow<AlbumDetails>
    suspend fun search(searchString: String): MutableList<Album>
}