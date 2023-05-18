package com.github.jayteealao.pastelmusic.app.mediaservice

import android.app.PendingIntent
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MusicService : MediaLibraryService() {
    private var mediaLibrarySession: MediaLibrarySession? = null

    @Inject
    lateinit var musicSessionCallback: MusicSessionCallback
//    @Inject
//    lateinit var musicNotificationProvider: MusicNotificationProvider

    override fun onCreate() {
        super.onCreate()

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
            .setUsage(C.USAGE_MEDIA)
            .build()

        val player = ExoPlayer.Builder(this)
            .setAudioAttributes(audioAttributes, true)
            .setHandleAudioBecomingNoisy(true)
            .build()

        mediaLibrarySession =
            MediaLibrarySession.Builder(this, player, musicSessionCallback).apply {
                setId(packageName)
                packageManager?.getLaunchIntentForPackage(packageName)?.let { sessionIntent ->
                    setSessionActivity(
                        PendingIntent.getActivity(
                            /* context= */ this@MusicService,
                            /* requestCode= */ 0,
                            sessionIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                }
            }.build()

//        setMediaNotificationProvider(musicNotificationProvider)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = mediaLibrarySession

    override fun onDestroy() {
        super.onDestroy()
        mediaLibrarySession?.run {
            player.release()
            release()
            mediaLibrarySession = null
        }
        musicSessionCallback.cancelCoroutineScope()
//        musicNotificationProvider.cancelCoroutineScope()
    }
}