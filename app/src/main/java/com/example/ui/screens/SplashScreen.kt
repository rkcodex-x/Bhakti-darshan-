package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronLight
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.TempleBrown
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    isHindi: Boolean,
    onNavigateToHome: () -> Unit
) {
    val scaleAnim = remember { Animatable(0.7f) }
    val alphaAnim = remember { Animatable(0f) }
    val sunRiseAnim = remember { Animatable(100f) }

    LaunchedEffect(Unit) {
        scaleAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        )
        alphaAnim.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 800)
        )
        sunRiseAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        )
        delay(2200)
        onNavigateToHome()
    }

    val sunriseBackground = Brush.verticalGradient(
        colors = listOf(
            SaffronPrimary,
            SaffronOrange,
            GoldPrimary,
            Color(0xFFFFF3E0)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(sunriseBackground)
            .clickable { onNavigateToHome() }
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        // Sunrise Rays and Temple Silhouette Drawing
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val sunCenter = Offset(w / 2f, h * 0.45f + sunRiseAnim.value)

            // Radiant Sun Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        PureWhite,
                        GoldLight.copy(alpha = 0.9f),
                        GoldPrimary.copy(alpha = 0.4f),
                        Color.Transparent
                    ),
                    center = sunCenter,
                    radius = w * 0.42f
                ),
                center = sunCenter,
                radius = w * 0.42f
            )

            // Temple Shikhar & Dome Silhouette at the bottom horizon
            val horizonY = h * 0.76f
            val templePath = Path().apply {
                moveTo(0f, h)
                lineTo(0f, horizonY + 20f)
                // Left dome
                lineTo(w * 0.15f, horizonY + 20f)
                lineTo(w * 0.20f, horizonY - 20f)
                lineTo(w * 0.25f, horizonY + 20f)

                // Central Grand Temple Sanctum
                lineTo(w * 0.35f, horizonY + 10f)
                lineTo(w * 0.40f, horizonY - 45f)
                // Main Shikhar Spire
                lineTo(w * 0.48f, horizonY - 110f)
                lineTo(w * 0.50f, horizonY - 130f) // Kalash tip
                lineTo(w * 0.52f, horizonY - 110f)
                lineTo(w * 0.60f, horizonY - 45f)
                lineTo(w * 0.65f, horizonY + 10f)

                // Right dome
                lineTo(w * 0.75f, horizonY + 20f)
                lineTo(w * 0.80f, horizonY - 20f)
                lineTo(w * 0.85f, horizonY + 20f)
                lineTo(w, horizonY + 20f)
                lineTo(w, h)
                close()
            }

            drawPath(
                path = templePath,
                color = TempleBrown.copy(alpha = 0.88f)
            )
        }

        // Center Divine Emblem (Om & Golden Light)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .scale(scaleAnim.value)
                .alpha(alphaAnim.value)
                .padding(horizontal = 24.dp)
        ) {
            // Sacred Golden Om Circle
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(PureWhite, GoldLight, GoldPrimary)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "ॐ",
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Bold,
                    color = TempleBrown
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (isHindi) "भक्ति दर्शन" else "Bhakti Darshan",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = PureWhite
                )
                Text(
                    text = " 🙏",
                    fontSize = 32.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle: भजन • दर्शन • राशिफल • पूजा
            Text(
                text = if (isHindi) "भजन • दर्शन • राशिफल • पूजा" else "Bhajan • Darshan • Rashifal • Puja",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = PureWhite.copy(alpha = 0.95f),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Tagline: “हर सुबह भगवान के साथ”
            Text(
                text = if (isHindi) "“हर सुबह भगवान के साथ”" else "“Every morning with the Divine”",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = GoldLight
            )
        }

        // Bottom tagline: आपका आध्यात्मिक साथी 🙏
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 24.dp)
                .alpha(alphaAnim.value)
        ) {
            Text(
                text = if (isHindi) "आपका आध्यात्मिक साथी 🙏" else "Your Spiritual Companion 🙏",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = PureWhite
            )
        }
    }
}
