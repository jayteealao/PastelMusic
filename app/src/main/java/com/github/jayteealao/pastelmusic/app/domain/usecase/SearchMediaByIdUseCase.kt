package com.github.jayteealao.pastelmusic.app.domain.usecase

import com.github.jayteealao.pastelmusic.app.di.dispatchers.Dispatcher
import com.github.jayteealao.pastelmusic.app.di.dispatchers.PastelDispatchers
import com.github.jayteealao.pastelmusic.app.domain.repository.AlbumRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.ArtistRepository
import com.github.jayteealao.pastelmusic.app.domain.repository.SongRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchMediaByIdUseCase @Inject constructor(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository,
    private val albumRepository: AlbumRepository,
    @Dispatcher(PastelDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(mediaId: String) = withContext(ioDispatcher) {
        songRepository.searchById(mediaId)
    }
}