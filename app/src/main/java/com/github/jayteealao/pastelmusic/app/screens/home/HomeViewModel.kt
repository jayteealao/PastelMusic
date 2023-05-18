package com.github.jayteealao.pastelmusic.app.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.jayteealao.pastelmusic.app.domain.model.Album
import com.github.jayteealao.pastelmusic.app.domain.model.Song
import com.github.jayteealao.pastelmusic.app.domain.usecase.GetAlbumDetailsUseCase
import com.github.jayteealao.pastelmusic.app.domain.usecase.GetAlbumsUseCase
import com.github.jayteealao.pastelmusic.app.domain.usecase.GetSongsUseCase
import com.github.jayteealao.pastelmusic.app.domain.usecase.PlayerControlsUseCase
import com.github.jayteealao.pastelmusic.app.mediaservice.MusicServiceConnection
import com.github.jayteealao.pastelmusic.app.mediaservice.util.PlayerControl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection,
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
    private val getSongsUseCase: GetSongsUseCase,
    private val playerControlsUseCase: PlayerControlsUseCase
): ViewModel() {
    val playbackState = musicServiceConnection.playbackState

    val albums : Flow<List<Album>> = getAlbumsUseCase()
    val songs = getSongsUseCase()

    fun playAlbum(album: Album) {
        viewModelScope.launch {
            musicServiceConnection.playSongs(
                getAlbumDetailsUseCase(album)
                    .first()
                    .songs
            )
        }
    }

    fun playSong(song: Song) = musicServiceConnection.playSongs(listOf(song))

    val controls: PlayerControl = playerControlsUseCase()
}