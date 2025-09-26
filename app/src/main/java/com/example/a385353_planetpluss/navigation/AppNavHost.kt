package com.example.a385353_planetpluss.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.a385353_planetpluss.ui.screens.AboutScreen
import com.example.a385353_planetpluss.ui.screens.GameScreen
import com.example.a385353_planetpluss.ui.screens.HomeScreen
import com.example.a385353_planetpluss.ui.screens.PrefScreen

/**
AppNavHost sets up the navigation graph for the app using Jetpack Compose Navigation.

- This Composable defines the app's routes and how to navigate between screens.
- It requires a NavHostController, which manages the current screen and navigation actions.
- The startDestination ("home") is the first screen displayed when the app launches.
- Each composable defines a route and the corresponding screen to display when navigated to.
 */

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("game") {
            GameScreen(navController = navController)
        }
        composable("preferences") {
            PrefScreen(navController = navController)
        }
        composable("about") {
            AboutScreen(navController = navController)
        }
    }
}