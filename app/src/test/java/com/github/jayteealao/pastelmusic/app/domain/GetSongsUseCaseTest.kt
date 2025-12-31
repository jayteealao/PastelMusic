package com.github.jayteealao.pastelmusic.app.domain

import app.cash.turbine.test
import com.github.jayteealao.pastelmusic.app.domain.usecase.GetSongsUseCase
import com.github.jayteealao.pastelmusic.app.testing.FakeSongRepository
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for GetSongsUseCase.
 */
class GetSongsUseCaseTest {

    private lateinit var repository: FakeSongRepository
    private lateinit var useCase: GetSongsUseCase

    @Before
    fun setup() {
        repository = FakeSongRepository()
        useCase = GetSongsUseCase(repository)
    }

    @Test
    fun `invoke returns flow of songs from repository`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        useCase().test {
            val songs = awaitItem()
            assertThat(songs).hasSize(5)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke returns empty list when no songs`() = runTest {
        useCase().test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke emits updates when songs change`() = runTest {
        useCase().test {
            assertThat(awaitItem()).isEmpty()

            repository.setSongs(TestData.sampleSongs.take(2))
            assertThat(awaitItem()).hasSize(2)

            repository.setSongs(TestData.sampleSongs)
            assertThat(awaitItem()).hasSize(5)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
