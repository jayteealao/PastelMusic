package com.github.jayteealao.pastelmusic.app.domain

import app.cash.turbine.test
import com.github.jayteealao.pastelmusic.app.testing.FakeSongRepository
import com.github.jayteealao.pastelmusic.app.testing.TestData
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for SongRepository operations using FakeSongRepository.
 */
class SongRepositoryTest {

    private lateinit var repository: FakeSongRepository

    @Before
    fun setup() {
        repository = FakeSongRepository()
    }

    @Test
    fun `getAll returns empty list initially`() = runTest {
        repository.getAll().test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAll returns songs after setSongs`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        repository.getAll().test {
            assertThat(awaitItem()).hasSize(5)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search finds songs by title`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        val results = repository.search("Essence")
        assertThat(results).hasSize(1)
        assertThat(results[0].title).isEqualTo("Essence")
    }

    @Test
    fun `search finds songs by artist`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        val results = repository.search("Burna")
        assertThat(results).hasSize(1)
        assertThat(results[0].artist).contains("Burna")
    }

    @Test
    fun `search is case insensitive`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        val results = repository.search("ESSENCE")
        assertThat(results).hasSize(1)
    }

    @Test
    fun `search returns empty for non-matching query`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        val results = repository.search("NonExistentSong")
        assertThat(results).isEmpty()
    }

    @Test
    fun `searchById finds song by exact id`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        val results = repository.searchById("3")
        assertThat(results).hasSize(1)
        assertThat(results[0].title).isEqualTo("Essence")
    }

    @Test
    fun `searchById returns empty for non-existent id`() = runTest {
        repository.setSongs(TestData.sampleSongs)

        val results = repository.searchById("999")
        assertThat(results).isEmpty()
    }

    @Test
    fun `addToQueue adds song to queue`() = runTest {
        val song = TestData.createSong()

        repository.addToQueue(song)

        repository.getPlayingQueue().test {
            assertThat(awaitItem()).hasSize(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addToQueue adds multiple songs to queue`() = runTest {
        repository.addToQueue(TestData.sampleSongs)

        repository.getPlayingQueue().test {
            assertThat(awaitItem()).hasSize(5)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clearQueue removes all songs from queue`() = runTest {
        repository.addToQueue(TestData.sampleSongs)
        repository.clearQueue()

        repository.getPlayingQueue().test {
            assertThat(awaitItem()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setPlayingQueueIndex updates queue index`() = runTest {
        repository.setPlayingQueueIndex(5)

        repository.getPlayingQueueIndex().test {
            assertThat(awaitItem()).isEqualTo(5)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setPlayingQueue replaces entire queue`() = runTest {
        repository.addToQueue(TestData.createSong(id = "old"))
        repository.setPlayingQueue(TestData.sampleSongs)

        repository.getPlayingQueue().test {
            val queue = awaitItem()
            assertThat(queue).hasSize(5)
            assertThat(queue.none { it.id == "old" }).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
