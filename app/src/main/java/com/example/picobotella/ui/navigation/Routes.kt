package com.example.picobotella.ui.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Home : Routes("home")
    object Instrucciones : Routes("instrucciones")
    object Retos : Routes("retos")
}
