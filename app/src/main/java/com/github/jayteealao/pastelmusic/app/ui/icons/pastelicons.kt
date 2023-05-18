package com.github.jayteealao.pastelmusic.app.ui.icons

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.jayteealao.pastelmusic.app.R

object PastelIcons {
    val Home = Icon.ImageVectorIcon(Icons.Rounded.Home)
    val Search = Icon.ImageVectorIcon(Icons.Rounded.Search)
    val Favorite = Icon.ImageVectorIcon(Icons.Rounded.Favorite)
    val Settings = Icon.ImageVectorIcon(Icons.Rounded.Settings)
    val ArrowBack = Icon.ImageVectorIcon(Icons.Rounded.ArrowBack)
    val Clear = Icon.ImageVectorIcon(Icons.Rounded.Clear)
    val Music = Icon.DrawableResourceIcon(R.drawable.ic_music)
    val Repeat = Icon.DrawableResourceIcon(R.drawable.ic_repeat)
    val RepeatOne = Icon.DrawableResourceIcon(R.drawable.ic_repeat_one)
    val Shuffle = Icon.DrawableResourceIcon(R.drawable.ic_shuffle)
    val SkipPrevious = Icon.DrawableResourceIcon(R.drawable.ic_skip_previous)
    val Play = Icon.DrawableResourceIcon(R.drawable.ic_play)
    val Pause = Icon.DrawableResourceIcon(R.drawable.ic_pause)
    val SkipNext = Icon.DrawableResourceIcon(R.drawable.ic_skip_next)
    val GitHub = Icon.DrawableResourceIcon(R.drawable.ic_github)
    val Info = Icon.ImageVectorIcon(Icons.Rounded.Info)
    val Security = Icon.DrawableResourceIcon(R.drawable.ic_security)
}

sealed interface Icon {
    data class ImageVectorIcon(val imageVector: ImageVector) : Icon
    data class DrawableResourceIcon(@DrawableRes val resourceId: Int) : Icon
}