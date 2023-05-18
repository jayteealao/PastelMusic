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

import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val albumRepository: AlbumRepository) {
    operator fun invoke(album: Album) = albumRepository.getAlbumSongs(album)
}
