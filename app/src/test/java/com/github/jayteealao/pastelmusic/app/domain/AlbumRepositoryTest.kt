package com.github.jayteealao.pastelmusic.app.domain

import app.cash.turbine.test
import com.github.jayteealao.pastelmusic.app.testing.FakeAlbumRepository
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for AlbumRepository operations using FakeAlbumRepository.
 */
class AlbumRepositoryTest {

    private lateinit var repository: FakeAlbumRepository

    @Before
    fun setup() {
        repository = FakeAlbumRepository()
    }

    @Test
    fun `getAll returns empty list initially`() = runTest {
        repository.getAll().test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAll returns albums after setAlbums`() = runTest {
        repository.setAlbums(TestData.sampleAlbums)

        repository.getAll().test {
            assertThat(awaitItem()).hasSize(5)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search finds albums by title`() = runTest {
        repository.setAlbums(TestData.sampleAlbums)

        val results = repository.search("African")
        assertThat(results).hasSize(1)
        assertThat(results[0].title).isEqualTo("African Giant")
    }

    @Test
    fun `search finds albums by artist`() = runTest {
        repository.setAlbums(TestData.sampleAlbums)

        val results = repository.search("Rema")
        assertThat(results).hasSize(1)
        assertThat(results[0].artist).isEqualTo("Rema")
    }

    @Test
    fun `search is case insensitive`() = runTest {
        repository.setAlbums(TestData.sampleAlbums)

        val results = repository.search("BOYFRIEND")
        assertThat(results).hasSize(1)
    }

    @Test
    fun `getAlbumSongs returns album details with songs`() = runTest {
        val album = TestData.sampleAlbums[0]
        val songs = listOf(
            TestData.createSong(id = "1", title = "Song 1", albumId = album.id),
            TestData.createSong(id = "2", title = "Song 2", albumId = album.id)
        )

        repository.setAlbums(listOf(album))
        repository.setAlbumSongs(album.id, songs)

        repository.getAlbumSongs(album).test {
            val details = awaitItem()
            assertThat(details.album).isEqualTo(album)
            assertThat(details.songs).hasSize(2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAlbumSongs returns empty songs for album without songs`() = runTest {
        val album = TestData.sampleAlbums[0]
        repository.setAlbums(listOf(album))

        repository.getAlbumSongs(album).test {
            val details = awaitItem()
            assertThat(details.songs).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
