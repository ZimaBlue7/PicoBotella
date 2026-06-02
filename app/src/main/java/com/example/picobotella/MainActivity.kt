package com.example.picobotella

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.picobotella.ui.navigation.AppNavigation
import com.example.picobotella.ui.theme.PicoBotellaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PicoBotellaTheme {
                AppNavigation()
            }
        }
    }
}
