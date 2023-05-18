package com.github.jayteealao.pastelmusic.app.database

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Artist
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.extensions.asArtworkUri
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class SongsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
    private val scope: CoroutineScope
) {

    private val _queue = MutableStateFlow<MutableList<Song>>(mutableListOf())
    val queue = _queue.filterNotNull<List<Song>>()

    private var currentIndex = -1

    private fun songsCursor(selection: String? = null): Cursor? {

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID
        )

        return context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection,
            selection, null, sort)
    }

    private fun albumsCursor(): Cursor? {
        val projection = arrayOf(
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.ALBUM,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ARTIST,
        )

        return context.contentResolver.query(
            MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection,
            null, null, MediaStore.Audio.Albums.ALBUM + " ASC")
    }

    private fun artistCursor(): Cursor? {
        val projection = arrayOf(MediaStore.Audio.Artists._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS)

        return context.contentResolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            MediaStore.Audio.Artists.ARTIST + " ASC")

    }

//    private fun songsCursorWithAlbumArt(selection: String? = null): Cursor? {
//        val projection = arrayOf(
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.ARTIST,
//            MediaStore.Audio.Media.ALBUM,
//            MediaStore.Audio.Media.TITLE,
//            MediaStore.Audio.Media.DISPLAY_NAME,
//            MediaStore.Audio.Media.DURATION,
//            MediaStore.Audio.Media.ALBUM_ID,
//            MediaStore.Images.Media.DATA,
////            MediaStore.Audio.Albums._ID
//        )
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.buildUpon()
//            .appendQueryParameter(MediaStore.EXTRA_MEDIA_ARTIST, "false")
//            .appendQueryParameter(MediaStore.EXTRA_MEDIA_ALBUM, "false")
//            .build()
//        val selectionWithArt = "(${MediaStore.Audio.Media.ALBUM_ID} IS NOT NULL) AND (${MediaStore.Audio.Media.IS_MUSIC} != 0) $selection"
//        return context.contentResolver.query(
//            uri,
//            projection,
//            selection,
//            null,
//            sort
//        )
//    }


    suspend fun all() = withContext(ioDispatcher) {
        val songInfo = mutableListOf<Song>()
        val c = songsCursor(selection)
        c?.apply {
            this.moveToFirst()
//            Log.d("Column-Names", "${this.columnNames.asList()}")
            while (!this.isAfterLast) {
                val item = this.toSong()
                songInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songInfo
    }

    suspend fun albums() = withContext(ioDispatcher) {
        val albums = mutableListOf<Album>()
        val c = albumsCursor()
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = c.toAlbum()
                albums.add(item)
                this.moveToNext()
            }
            c.close()
        }
        albums
    }

    suspend fun artists() = withContext(ioDispatcher) {
        val artists = mutableListOf<Artist>()
        val c = artistCursor()
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = c.toArtist()
                artists.add(item)
                this.moveToNext()
            }
            c.close()
        }
        artists
    }

    suspend fun songs(album: Album) = withContext(ioDispatcher) {
        val songsInfo = mutableListOf<Song>()
        val query = MediaStore.Audio.Media.ALBUM_ID + " = '" + album.id + "'"
        val c = songsCursor(query)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSong()
                songsInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songsInfo
    }

    suspend fun songs(artist: Artist) = withContext(ioDispatcher) {
        val songsInfo = mutableListOf<Song>()
        val query = "$selection AND ${MediaStore.Audio.Media.ARTIST_ID}  = '${artist.id}'"
        val c = songsCursor(query)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSong()
                songsInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songsInfo
    }

    suspend fun search(searchString: String) = withContext(ioDispatcher) {
        val songsInfo = mutableListOf<Song>()
        val query = "$selection AND LOWER(${MediaStore.Audio.Media.TITLE} ) LIKE LOWER('$searchString%') "
        val c = songsCursor(query)
        c?.apply {
            this.moveToFirst()
            while (!this.isAfterLast) {
                val item = this.toSong()
                songsInfo.add(item)
                this.moveToNext()
            }
            c.close()
        }
        songsInfo
    }


    fun next(): Song? {
        return if (_queue.value.size - 1 > currentIndex) {
            currentIndex += 1
            _queue.value[currentIndex]
        } else {
            null
        }
    }


    fun previous(): Song? {
        return if (currentIndex >= 1) {
            currentIndex -= 1
            _queue.value[currentIndex]
        } else {
            null
        }
    }


    fun queue(song: Song) = scope.launch {
        val currentSongs = _queue.value.toMutableList()
        currentSongs.add(song)
        scope.launch {
            _queue.value = currentSongs
        }
    }


    fun queueAll(songs: List<Song>) {
        val currentSongs = _queue.value.toMutableList()
        currentSongs.addAll(songs)
        scope.launch {
            _queue.value = currentSongs
        }
    }


    fun addAtStart(song: Song) = scope.launch {

        val songs = _queue.value.toMutableList()
        val songExpectedIndex = songs.indexOf(song)
        currentIndex = if (songExpectedIndex != -1) {
            songExpectedIndex
        } else {
            songs.add(0, song)
            scope.launch {
                _queue.value = songs
            }
            0
        }
    }


    fun clear() {
        scope.launch {
            _queue.value = mutableListOf()
        }
    }


    companion object {
        const val sort = "LOWER(${MediaStore.Audio.Media.TITLE})"
        const val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND LOWER(${MediaStore.Audio.Media.DISPLAY_NAME})  NOT LIKE  LOWER('%.wma') "

        @SuppressLint("InlinedApi")
        val projection = arrayOf(MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ALBUM_ID)
    }

    @SuppressLint("InlinedApi", "Range")
    private fun Cursor.toSong(): Song {
//        Log.d("songrepo", getString(getColumnIndex(MediaStore.Audio.Media.TITLE)))
//        Log.d("songrepo2", getString(getColumnIndex(MediaStore.Images.Media.DATA)).toString())
        return Song(
            getString(getColumnIndex(MediaStore.Audio.Media.TITLE)),
            getString(getColumnIndex(MediaStore.Audio.Media.ARTIST)),
            getString(getColumnIndex(MediaStore.Audio.Media.ALBUM)),
            getString(getColumnIndex(MediaStore.Audio.Media._ID)),
            getString(getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
            ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, getLong(getColumnIndex(MediaStore.Audio.Media._ID))),
            getLong(getColumnIndex(MediaStore.Audio.Media.DURATION)),
            getLong(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).asArtworkUri(),
            getString(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
            getString(getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
        )
    }

    @SuppressLint("Range")
    private fun Cursor.toAlbum(): Album {
//        Log.d("songrepo", getString(getColumnIndex(MediaStore.Audio.Media.ALBUM)))
        return Album(
            getString(getColumnIndex(MediaStore.Audio.Media._ID)),
            getString(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
            getString(getColumnIndex(MediaStore.Audio.Media.ALBUM)),
            getInt(getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS)),
            getString(getColumnIndex(MediaStore.Audio.Albums.ARTIST))
        )
    }

    @SuppressLint("Range")
    private fun Cursor.toArtist(): Artist {
        return Artist(
            getString(getColumnIndex(MediaStore.Audio.Artists._ID)),
            getString(getColumnIndex(MediaStore.Audio.Artists.ARTIST)),
            getInt(getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))
        )
    }


}