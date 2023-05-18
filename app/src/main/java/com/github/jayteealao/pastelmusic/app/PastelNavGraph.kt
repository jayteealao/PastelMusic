package com.github.jayteealao.pastelmusic.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.jayteealao.pastelmusic.app.screens.home.HomeScreen
import com.github.jayteealao.pastelmusic.app.screens.home.HomeViewModel

@Composable
fun PastelNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "Home",
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(
            route = "Home",
        )   {
            HomeScreen(homeViewModel)
        }
    }
}