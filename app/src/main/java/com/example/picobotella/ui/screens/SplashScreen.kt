package com.example.picobotella.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.picobotella.R
import com.example.picobotella.ui.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // Animación de rotación infinita para la botella
    val infiniteTransition = rememberInfiniteTransition(label = "bottleRotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    LaunchedEffect(key1 = true) {
        delay(5000) // 5 segundos según el plan
        navController.navigate(Routes.Home.route) {
            popUpTo(Routes.Splash.route) { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Fondo negro
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centrado vertical
    ) {

        Image(
            painter = painterResource(id = R.drawable.botella),
            contentDescription = "Botella",
            modifier = Modifier
                .size(250.dp)
                .rotate(rotation), // Animación de rotación
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Texto Naranja "PICO BOTELLA"
        Text(
            text = "PICO BOTELLA",
            color = Color(0xFFFFA500), // Naranja
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
