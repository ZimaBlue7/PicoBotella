package com.example.picobotella.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.picobotella.R

@Composable
fun HomeScreen(navController: NavHostController) {
    // Estado para el contador (empieza en 3 según HU 2.0)
    var counter by remember { mutableStateOf("3") }
    
    // Animación de escala para el efecto de parpadeo (pulso) en el botón
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo de madera
        Image(
            painter = painterResource(id = R.drawable.piso_madera),
            contentDescription = "Fondo de madera",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Contenedor de la botella y el contador
        Box(
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            // 2. Imagen de botella centrada
            Image(
                painter = painterResource(id = R.drawable.botella),
                contentDescription = "Botella",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // 4. Contador en el centro de la botella
            Text(
                text = counter,
                color = Color.White,
                fontSize = 60.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 20.dp) // Ajuste leve según la forma de tu botella
            )
        }

        // 5. Botón naranja parpadeante "PRESIONAME"
        Button(
            onClick = { 
                // Aquí irá la lógica de giro de la HU 11.0 más adelante
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
                .scale(scale), // Efecto de parpadeo
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500) // Naranja
            )
        ) {
            Text(
                text = "PRESIONAME",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}
