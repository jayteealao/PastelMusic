package com.github.jayteealao.pastelmusic.app.domain.repository

import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.ArtistDetails
import kotlinx.coroutines.flow.Flow

interface ArtistRepository {
    fun getAll(): Flow<List<Artist>>
    fun getArtistSongs(artist: Artist): Flow<ArtistDetails>
    suspend fun search(searchString: String): MutableList<Artist>

}