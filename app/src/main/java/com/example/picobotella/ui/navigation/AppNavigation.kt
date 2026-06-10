package com.example.picobotella.ui.navigation

import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.picobotella.ui.screens.HomeScreen
import com.example.picobotella.ui.screens.SplashScreen
import com.example.picobotella.ui.screens.Fragment
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
        //Una vez realizadas las screens de instrucciones y retos, se deben agregar aquí
        composable(Routes.Instrucciones.route) {
            Fragment(navController)
        }
        composable(Routes.Retos.route) {
            Fragment(navController)
        }

        // Agregaremos las demás pantallas (Instrucciones, Retos) en los siguientes pasos
    }
}
