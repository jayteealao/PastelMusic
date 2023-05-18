/*
 * Copyright 2022 Maximillian Leonov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.jayteealao.pastelmusic.app.domain.usecase

import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.SearchDetails
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.domain.repository.AlbumRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.ArtistRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.SongRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class SearchMediaUseCase @Inject constructor(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {

    private val scope = CoroutineScope(ioDispatcher + SupervisorJob())

    operator fun invoke(query: String) {
        val songsFlow: Flow<List<Song>> = flow {
            emit(songRepository.search(query))
        }
        val albumsFlow: Flow<List<Album>> = flow {
            emit(albumRepository.search(query))
        }
        val artistsFlow: Flow<List<Artist>> = flow {
            emit(artistRepository.search(query))
        }

        combine(
            songsFlow,
            artistsFlow,
            albumsFlow,
        ) { songs, artists, albums -> SearchDetails(songs, artists, albums) }
            .mapLatest { searchDetails ->
                if (query.isBlank()) {
                    return@mapLatest searchDetails.copy(
                        songs = emptyList(),
                        artists = emptyList(),
                        albums = emptyList()
                    )
                } else {
                    searchDetails
                }

            }
    }
}

