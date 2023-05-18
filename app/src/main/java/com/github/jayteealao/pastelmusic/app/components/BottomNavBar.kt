package com.github.jayteealao.pastelmusic.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.jayteealao.pastelmusic.app.R
import com.github.jayteealao.pastelmusic.app.ui.components.PastelLayout
import com.github.jayteealao.pastelmusic.app.ui.theme.PastelTheme

//TODO: home, fav, playlist/queue, settings
@Composable
fun BottomNavBar() {
    var selectedOption by remember {
        mutableStateOf(BottomNavBarOptions.HOME)
    }
    PastelLayout(color = Color.White) {
        
        BottomNavigation(
            backgroundColor = PastelTheme.colors.pastelBlue
        ) {
            BottomNavigationItem(
                selected = selectedOption == BottomNavBarOptions.HOME,
                onClick = { selectedOption = BottomNavBarOptions.HOME },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.home_variant),
                    contentDescription = "Home Navigation Icon"
                ) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,
                modifier = Modifier
                    .then(
                        if (selectedOption == BottomNavBarOptions.HOME) {
                            Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = PastelTheme.colors.pastelYellow)
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                        } else Modifier
                    )
            )
            BottomNavigationItem(
                selected = selectedOption == BottomNavBarOptions.FAVORITES,
                onClick = { selectedOption = BottomNavBarOptions.FAVORITES },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.fire),
                    contentDescription = "Home Navigation Icon"
                ) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,
                modifier = Modifier
                    .then(
                        if (selectedOption == BottomNavBarOptions.FAVORITES) {
                            Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = PastelTheme.colors.pastelYellow)
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                        } else Modifier
                    )
            )
            BottomNavigationItem(
                selected = selectedOption == BottomNavBarOptions.MUSIC,
                onClick = { selectedOption = BottomNavBarOptions.MUSIC },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.disc),
                    contentDescription = "Home Navigation Icon"
                ) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,
                modifier = Modifier
                    .then(
                        if (selectedOption == BottomNavBarOptions.MUSIC) {
                            Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = PastelTheme.colors.pastelYellow)
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                        } else Modifier
                    )
            )
            BottomNavigationItem(
                selected = selectedOption == BottomNavBarOptions.SETTINGS,
                onClick = { selectedOption = BottomNavBarOptions.SETTINGS },
                icon = { Icon(
                    painter = painterResource(id = R.drawable.cog),
                    contentDescription = "Home Navigation Icon"
                ) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.White,
                modifier = Modifier
                    .then(
                        if (selectedOption == BottomNavBarOptions.SETTINGS) {
                            Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color = PastelTheme.colors.pastelYellow)
                                .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                        } else Modifier
                    )
            )
        }
    }
}

enum class BottomNavBarOptions {
    HOME,
    FAVORITES,
    MUSIC,
    SETTINGS
}

@Preview
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar()
}