package com.example.picobotella.ui.screens

import android.content.Intent
import android.net.Uri
import android.media.MediaPlayer
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
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
import com.example.picobotella.data.remote.RetrofitInstance
import com.example.picobotella.data.repository.PokemonRepositoryImpl
import androidx.compose.ui.draw.rotate
import kotlin.random.Random
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import android.util.Log
import kotlinx.coroutines.launch
import com.example.picobotella.ui.screens.ChallengeDialog

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current

    //estados usados en esta vista


    var isSpinning by remember {
        mutableStateOf(false)
    }

    var showCountdown by remember {
        mutableStateOf(false)
    }

    var countdown by remember {
        mutableIntStateOf(0)
    }

    val rotationAnimation = remember {
        Animatable(0f)
    }

    val scope = rememberCoroutineScope()

    var isSoundOn by remember { mutableStateOf(true) }

    var pokemonImageUrl by remember {
        mutableStateOf("")
    }

    var showChallenge by remember {
        mutableStateOf(false)
    }

    val pokemonRepository = remember {
        PokemonRepositoryImpl(
            RetrofitInstance.api
        )
    }

    // Música de fondo (HU 2.0) - Usando fondomusica.mp3
    val mediaPlayer = remember {
        try {
            val resId = context.resources.getIdentifier("fondomusica", "raw", context.packageName)
            if (resId != 0) MediaPlayer.create(context, resId) else null
        } catch (e: Exception) {
            null
        }
    }

    val spinPlayer = remember {
        try {
            MediaPlayer.create(
                context,
                R.raw.bottle_rolling
            )
        } catch (e: Exception) {
            null
        }
    }

    LaunchedEffect(Unit) {
        spinPlayer?.setVolume(1f, 1f)
    }

    LaunchedEffect(isSoundOn) {
        if (isSoundOn) {
            mediaPlayer?.apply {
                isLooping = true
                start()
            }
        } else {
            mediaPlayer?.pause()
        }
    }

    DisposableEffect(Unit) {
        onDispose {

            mediaPlayer?.apply {
                if (isPlaying) stop()
                release()
            }

            spinPlayer?.apply {
                if (isPlaying) stop()
                release()
            }
        }
    }

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

        // 2. Toolbar (HU 3.0)
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .wrapContentSize(),
            shape = RoundedCornerShape(30.dp),
            color = Color.Black.copy(alpha = 0.7f)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Estrella (Calificar)
                ToolbarIcon(id = R.drawable.start) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nequi.MobileApp"))
                    context.startActivity(intent)
                }
                
                // Audio ON/OFF
                ToolbarIcon(id = if (isSoundOn) R.drawable.sound else R.drawable.no_sound) {
                    isSoundOn = !isSoundOn
                }

                // Instrucciones
                ToolbarIcon(id = R.drawable.add_symbol) {
                    navController.navigate(Routes.Instrucciones.route)
                }

                // Retos
                ToolbarIcon(id = R.drawable.control_videogame) {
                    navController.navigate(Routes.Retos.route)
                }

                // Compartir
                ToolbarIcon(id = R.drawable.outline_share_24) {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "¡Juega Pico Botella! https://play.google.com/store/apps/details?id=com.nequi.MobileApp")
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, null))
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
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotationAnimation.value),
                contentScale = ContentScale.Fit
            )
            if (showCountdown) {
                Text(
                    text = countdown.toString(),
                    color = Color.White,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(top = 20.dp)
                )
            }
        }

        // 5. Botón naranja parpadeante "PRESIONAME"
        if (!isSpinning && !showCountdown) {

            Button(
                onClick = {
                    scope.launch {

                        try {

                            val response = RetrofitInstance.api.getPokedex()

                            val randomPokemon =
                                pokemonRepository.getRandomPokemon()

                            pokemonImageUrl =
                                randomPokemon.img.replace(
                                    "http://",
                                    "https://"
                                )

                        } catch (e: Exception) {

                            Log.e(
                                "POKEMON_TEST",
                                "ERROR: ${e.message}",
                                e
                            )
                        }

                        isSpinning = true
                        mediaPlayer?.pause()
                        spinPlayer?.start()


                        val extraRotation = Random.nextInt(
                            1080,
                            1800
                        ).toFloat()

                        rotationAnimation.animateTo(
                            targetValue =
                                rotationAnimation.value + extraRotation,

                            animationSpec = tween(
                                durationMillis = Random.nextInt(3000, 5000),
                                easing = FastOutSlowInEasing
                            )
                        )

                        spinPlayer?.pause()
                        spinPlayer?.seekTo(0)

                        showCountdown = true

                        for (i in 3 downTo 0) {
                            countdown = i
                            delay(1000)
                        }

                        showCountdown = false

                        showChallenge = true
                    }
                },
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
    if (showChallenge) {

        ChallengeDialog(
            challengeText = "falta traer info de la db...",
            pokemonImageUrl = pokemonImageUrl,
            onClose = {

                showChallenge = false
                isSpinning = false

                if (isSoundOn) {
                    mediaPlayer?.start()
                }
            }
        )
    }
}

@Composable
fun ToolbarIcon(id: Int, onClick: () -> Unit) {
    Icon(
        painter = painterResource(id = id),
        contentDescription = null,
        tint = Color(0xFFFFA500),
        modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    )
}
