package com.example.picobotella.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.picobotella.ui.screens.HomeScreen
import com.example.picobotella.ui.screens.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }
        // Las rutas de Instrucciones y Retos se activarán cuando terminemos sus pantallas
        composable(Routes.Instrucciones.route) {
            // Pantalla temporal o placeholder
        }
        composable(Routes.Retos.route) {
            // Pantalla temporal o placeholder
        }
    }
}
