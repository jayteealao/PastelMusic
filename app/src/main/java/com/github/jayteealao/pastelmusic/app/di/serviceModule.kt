package com.github.jayteealao.pastelmusic.app.di

import android.content.Context
import com.github.jayteealao.pastelmusic.app.database.SongsRepository
import com.github.jayteealao.pastelmusic.app.player.LocalSource
import com.github.jayteealao.pastelmusic.app.player.MusicSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    fun providesSongRepository(
        @ApplicationContext context: Context,
//        coroutineDispatcher: CoroutineDispatcher,
//        coroutineScope: CoroutineScope
    ): SongsRepository {
        return SongsRepository(context, Dispatchers.IO, CoroutineScope(Dispatchers.IO))
    }

    @Provides
    fun providesLocalSource (songsRepository: SongsRepository): MusicSource {
        return LocalSource(songsRepository)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
object songRepoModule {
//    @Provides
//    fun providesCoroutineDispatcher() : CoroutineDispatcher {
//        return Dispatchers.IO
//    }
//
//    @Provides
//    fun providesCoroutineScope(coroutineDispatcher: CoroutineDispatcher): CoroutineScope {
//        return CoroutineScope(coroutineDispatcher)
//    }

    @Provides
    fun providesSongRepository(
        @ApplicationContext context: Context,
//        coroutineDispatcher: CoroutineDispatcher,
//        coroutineScope: CoroutineScope
    ): SongsRepository {
        return SongsRepository(context, Dispatchers.IO, CoroutineScope(Dispatchers.IO))
    }
}