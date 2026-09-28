package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PureWhite
import com.example.ui.theme.SaffronOrange
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VermilionKumkum
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadiantAuraBackground(
    modifier: Modifier = Modifier,
    baseColor: Color = SaffronPrimary
) {
    val transition = rememberInfiniteTransition(label = "auraTransition")
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(24000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val pulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxRadius = (size.minDimension / 2f) * pulse

        // Glowing radial aura
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    GoldLight.copy(alpha = 0.35f),
                    GoldPrimary.copy(alpha = 0.20f),
                    baseColor.copy(alpha = 0.08f),
                    Color.Transparent
                ),
                center = center,
                radius = maxRadius
            ),
            center = center,
            radius = maxRadius
        )

        // Sacred Radiant Rays
        rotate(rotation, pivot = center) {
            val rayCount = 16
            for (i in 0 until rayCount) {
                val angle = (i * (360f / rayCount)) * (PI / 180f)
                val endX = center.x + (maxRadius * 0.95f) * cos(angle).toFloat()
                val endY = center.y + (maxRadius * 0.95f) * sin(angle).toFloat()

                drawLine(
                    color = GoldLight.copy(alpha = if (i % 2 == 0) 0.30f else 0.15f),
                    start = center,
                    end = Offset(endX, endY),
                    strokeWidth = if (i % 2 == 0) 2.5f else 1.2f
                )
            }
        }
    }
}

/**
 * Interactive Aarti Thali with revolving brass rim, flickering diya lamp, and flower petals
 */
@Composable
fun InteractiveAartiThali(
    modifier: Modifier = Modifier,
    onRotate: () -> Unit = {}
) {
    var manualAngle by remember { mutableFloatStateOf(0f) }

    val transition = rememberInfiniteTransition(label = "diyaFlicker")
    val flameFlicker by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(450, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameFlicker"
    )

    val autoRot by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(16000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "autoRot"
    )

    Box(
        modifier = modifier
            .size(220.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    manualAngle += dragAmount.x * 0.8f
                    onRotate()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = size.minDimension / 2f - 12f
            val currentRot = manualAngle + autoRot

            // 1. Brass Plate Outer Rim & Gradient
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        GoldLight,
                        GoldPrimary,
                        Color(0xFFB78103)
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // Inner ornate brass border ring
            drawCircle(
                color = PureWhite.copy(alpha = 0.5f),
                radius = radius * 0.92f,
                center = center,
                style = Stroke(width = 3f)
            )

            drawCircle(
                color = Color(0xFF8D6E63).copy(alpha = 0.4f),
                radius = radius * 0.85f,
                center = center,
                style = Stroke(width = 1.5f)
            )

            // 2. Decorative Kumkum & Haldi Dots on plate rim
            rotate(currentRot, pivot = center) {
                val dots = 12
                for (i in 0 until dots) {
                    val angle = (i * (360f / dots)) * (PI / 180f)
                    val dotRadius = radius * 0.88f
                    val dotX = center.x + dotRadius * cos(angle).toFloat()
                    val dotY = center.y + dotRadius * sin(angle).toFloat()

                    drawCircle(
                        color = if (i % 2 == 0) VermilionKumkum else SaffronOrange,
                        radius = 4.5f,
                        center = Offset(dotX, dotY)
                    )
                }

                // 3. Flower Petals on Plate (Marigold & Rose)
                val petalCount = 8
                for (p in 0 until petalCount) {
                    val angle = (p * (360f / petalCount) + 20) * (PI / 180f)
                    val petalDist = radius * 0.55f
                    val px = center.x + petalDist * cos(angle).toFloat()
                    val py = center.y + petalDist * sin(angle).toFloat()

                    drawCircle(
                        color = if (p % 2 == 0) SaffronOrange else VermilionKumkum,
                        radius = 7f,
                        center = Offset(px, py)
                    )
                }
            }

            // 4. Center Diya Brass Base
            val diyaRadius = radius * 0.32f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(GoldAccent, Color(0xFFD48B00), Color(0xFF5D4037)),
                    center = center,
                    radius = diyaRadius
                ),
                radius = diyaRadius,
                center = center
            )

            // 5. Sacred Diya Sacred Jyoti Flame (Center)
            val flameH = diyaRadius * 1.5f * flameFlicker
            val flameW = diyaRadius * 0.9f * flameFlicker

            // Flame glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        PureWhite,
                        GoldLight.copy(alpha = 0.8f),
                        SaffronOrange.copy(alpha = 0.3f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = flameH * 1.4f
                ),
                radius = flameH * 1.4f,
                center = center
            )

            // Core white flame
            drawCircle(
                color = PureWhite,
                radius = flameW * 0.35f,
                center = center
            )
        }
    }
}
