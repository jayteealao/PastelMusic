package com.github.jayteealao.pastelmusic.app.datasource.util

import android.content.ContentResolver
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


fun <T> observeMediaStoreChanges(
    contentUri: Uri,
    contentResolver: ContentResolver,
    getData: suspend () -> T
): Flow<T> {
    return callbackFlow {
        // Create a content observer to listen for changes to the media store
        val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
            override fun onChange(selfChange: Boolean, uri: Uri?) {
                // Get the updated list of songs from the media store and re-emit it
                this@callbackFlow.launch {
                    val data = getData()
                    trySend(data)
                }
            }
        }

        contentResolver.registerContentObserver(
            contentUri,
            true,
            contentObserver
        )

        val initialData = getData()
        trySend(initialData)

        awaitClose {
            contentResolver.unregisterContentObserver(contentObserver)
        }
    }
}

