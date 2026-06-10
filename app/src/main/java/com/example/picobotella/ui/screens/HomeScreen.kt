package com.example.picobotella.ui.screens


import android.content.Intent
import android.net.Uri
import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.picobotella.ui.navigation.Routes

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    // Estado para el sonido (Encendido por defecto según requerimiento)
    var isSoundOn by remember { mutableStateOf(true) }

    // Estado para el contador (HU 2.0)
    var counter by remember { mutableStateOf("3") }


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
    // Animación de escala para el botón "PRESIONAME"
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

        // 2. Toolbar Flotante Personalizada (Centro Superior)
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp) // Distancia desde la parte superior
                .wrapContentSize(),
            shape = RoundedCornerShape(30.dp),
            color = Color.Black,
            shadowElevation = 10.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono 1: Play Store (Nequi)
                ToolbarIcon(id = R.drawable.start) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"))
                    context.startActivity(intent)
                }

                // Icono 2: Nueva ventana (Instrucciones)
                ToolbarIcon(id = R.drawable.add_symbol) {
                    navController.navigate(Routes.Instrucciones.route)
                }

                // Icono 3: Interruptor de Audio (Transformable)
                ToolbarIcon(id = if (isSoundOn) R.drawable.sound else R.drawable.no_sound) {
                    isSoundOn = !isSoundOn
                    // Nota: Aquí se debe llamar a la pausa/reinicio del MediaPlayer
                }

                // Icono 4: Retos del juego
                ToolbarIcon(id = R.drawable.control_videogame) {
                    navController.navigate(Routes.Retos.route)
                }

                // Icono 5: Compartir App (Bottom Sheet del sistema)
                ToolbarIcon(id = R.drawable.outline_share_24) {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "¡Juega a Pico Botella conmigo! Descárgala ya.")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Compartir Pico Botella vía:")
                    context.startActivity(shareIntent)
                }
            }
        }

        // Contenedor central (Botella y Contador)
        Box(
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.botella),
                contentDescription = "Botella",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
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

@Composable
fun ToolbarIcon(id: Int, onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = id),
        contentDescription = null,
        tint = Color(0xFFFFA500), // Color Naranja para los iconos
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    )
}
