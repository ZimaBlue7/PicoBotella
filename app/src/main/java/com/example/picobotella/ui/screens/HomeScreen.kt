package com.example.picobotella.ui.screens

import android.media.MediaPlayer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.picobotella.R

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    
    // Música de fondo (HU 2.0)
    // Se usa un bloque try-catch por si el archivo sonido_fondo aún no existe
    val mediaPlayer = remember {
        try {
            // Buscamos el recurso por nombre dinámicamente para evitar errores de compilación
            val resId = context.resources.getIdentifier("sonido_fondo", "raw", context.packageName)
            if (resId != 0) MediaPlayer.create(context, resId) else null
        } catch (e: Exception) {
            null
        }
    }

    DisposableEffect(Unit) {
        mediaPlayer?.apply {
            isLooping = true
            start()
        }
        onDispose {
            mediaPlayer?.apply {
                if (isPlaying) stop()
                release()
            }
        }
    }

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
                text = "3",
                color = Color.White,
                fontSize = 80.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(top = 20.dp)
            )
        }

        // 5. Botón naranja parpadeante "PRESIONAME"
        Button(
            onClick = { /* Próxima etapa: HU 11.0 Giro de botella */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
                .scale(scale),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFA500)
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
