package com.example.picobotella.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ChallengeDialog(
    challengeText: String,
    pokemonImageUrl: String,
    onClose: () -> Unit
) {

    Dialog(
        onDismissRequest = {
            // no se hace nada.
            // La HU pide que no se cierre tocando afuera.
        }
    ) {

        Box(
            contentAlignment = Alignment.TopCenter
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.DarkGray.copy(alpha = 0.55f),
                                Color.Black.copy(alpha = 0.95f),
                                Color.DarkGray.copy(alpha = 0.55f)
                            )
                        )
                    )
                    .padding(
                        top = 90.dp,
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 70.dp
                    )
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = challengeText,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    model = pokemonImageUrl,
                    contentDescription = "Pokemon",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }

            Button(
                onClick = onClose,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 25.dp)
                    .width(180.dp)
                    .height(55.dp),

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFA500)
                )
            ) {

                Text(
                    text = "Cerrar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold

                )
            }
        }
    }
}