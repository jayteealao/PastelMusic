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

package com.github.jayteealao.pastelmusic.app.di

import com.github.jayteealao.pastelmusic.app.data.AlbumRepositoryImpl
import com.github.jayteealao.pastelmusic.app.data.ArtistRepositoryImpl
import com.github.jayteealao.pastelmusic.app.data.SongRepositoryImpl
import com.github.jayteealao.pastelmusic.app.domain.repository.AlbumRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.ArtistRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.SongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindSongRepository(songRepositoryImpl: SongRepositoryImpl): SongRepository

    @Binds
    fun bindArtistRepository(artistRepositoryImpl: ArtistRepositoryImpl): ArtistRepository

    @Binds
    fun bindAlbumRepository(albumRepositoryImpl: AlbumRepositoryImpl): AlbumRepository
//
//    @Binds
//    fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
