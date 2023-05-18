package com.github.jayteealao.pastelmusic.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val pastelGray100Color = Color(0xFFfbf8f2)
val pastelGray200Color = Color(0xFFfcfdfe)
val pastelGray300Color = Color(0xFFe9ebec)
val pastelGray400Color = Color(0xFFe3e4ea)
val pastelGray500Color = Color(0xFF5f5f5f)
val pastelGray900Color = Color(0xFF1d1d1d)
val pastelLightBlueColor = Color(0xFFa8dee2)
val pastelBlueColor = Color(0xFF2ab3c0)
val pastelGreenColor = Color(0xFF80b895)
val pastelLightGreenColor = Color(0xFFbad5ca)
val pastelRedColor = Color(0xFFea605e)
val pastelLightYellowColor = Color(0xFFf8e0b1)
val pastelYellowColor = Color(0xFFf9bc73)
//private val LightColors = lightColors(
//    primary = Color.White,
//    secondary = pastelBlue,
//    secondaryVariant = pastelLightBlue,
//    background = Color.White,
//    surface = Color.White,
//    primaryVariant = pastelBrown
//
//)

data class PastelColors(
    val pastelBlue: Color = pastelBlueColor,
    val pastelGreen: Color = pastelGreenColor,
    val pastelLightBlue: Color = pastelLightBlueColor,
    val pastelGray100: Color = pastelGray100Color,
    val pastelGray200: Color = pastelGray200Color,
    val pastelGray300: Color = pastelGray300Color,
    val pastelGray400: Color = pastelGray400Color,
    val pastelGray500: Color = pastelGray500Color,
    val pastelGray900: Color = pastelGray900Color,
    val pastelLightGreen: Color = pastelLightGreenColor,
    val pastelRed: Color = pastelRedColor,
    val pastelLightYellow: Color = pastelLightYellowColor,
    val pastelYellow: Color = pastelYellowColor,
)

val LocalPastelColors = staticCompositionLocalOf {
    PastelColors()
}