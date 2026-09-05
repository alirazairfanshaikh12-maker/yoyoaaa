package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.random.Random

data class Particle(
    val xRatio: Float,
    val initialYRatio: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val type: ParticleType,
    val rotationSpeed: Float
)

enum class ParticleType {
    HEART, STAR, SPARKLE, CONFETTI
}

@Composable
fun CelebrationParticles(
    trigger: Long,
    modifier: Modifier = Modifier
) {
    if (trigger == 0L) return

    val particles = remember(trigger) {
        val colors = listOf(
            Color(0xFFFFD700), // Gold
            Color(0xFFFFA07A), // Light Salmon
            Color(0xFFFF69B4), // Hot Pink
            Color(0xFFFFF8DC), // Cornsilk
            Color(0xFFE6C687), // Warm Gold
            Color(0xFFFF85A2)  // Rose
        )
        List(40) {
            Particle(
                xRatio = Random.nextFloat(),
                initialYRatio = 0.85f + Random.nextFloat() * 0.15f,
                speed = 0.6f + Random.nextFloat() * 0.6f,
                size = 12f + Random.nextFloat() * 16f,
                color = colors[Random.nextInt(colors.size)],
                type = ParticleType.values()[Random.nextInt(ParticleType.values().size)],
                rotationSpeed = (Random.nextFloat() - 0.5f) * 360f
            )
        }
    }

    val progress = remember(trigger) { Animatable(0f) }

    LaunchedEffect(trigger) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 2400, easing = FastOutSlowInEasing)
        )
    }

    if (progress.value < 1f) {
        Canvas(modifier = modifier.fillMaxSize()) {
            val p = progress.value
            val alpha = (1f - p * 0.9f).coerceIn(0f, 1f)

            particles.forEach { particle ->
                val x = particle.xRatio * size.width + kotlin.math.sin((p * 4 + particle.xRatio * 10).toDouble()).toFloat() * 24f
                val y = (particle.initialYRatio - (p * particle.speed)) * size.height
                val currentAlpha = alpha * particle.color.alpha

                if (y in 0f..size.height) {
                    rotate(
                        degrees = p * particle.rotationSpeed,
                        pivot = Offset(x, y)
                    ) {
                        when (particle.type) {
                            ParticleType.HEART -> {
                                drawHeart(
                                    center = Offset(x, y),
                                    size = particle.size,
                                    color = particle.color.copy(alpha = currentAlpha)
                                )
                            }
                            ParticleType.STAR, ParticleType.SPARKLE -> {
                                drawStar(
                                    center = Offset(x, y),
                                    size = particle.size,
                                    color = particle.color.copy(alpha = currentAlpha)
                                )
                            }
                            ParticleType.CONFETTI -> {
                                drawCircle(
                                    color = particle.color.copy(alpha = currentAlpha),
                                    radius = particle.size / 3f,
                                    center = Offset(x, y)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawHeart(center: Offset, size: Float, color: Color) {
    val r = size / 2.5f
    // Draw two circles and a bottom triangle approximation
    drawCircle(
        color = color,
        radius = r,
        center = Offset(center.x - r * 0.7f, center.y - r * 0.5f)
    )
    drawCircle(
        color = color,
        radius = r,
        center = Offset(center.x + r * 0.7f, center.y - r * 0.5f)
    )
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(center.x - r * 1.5f, center.y - r * 0.2f)
        lineTo(center.x + r * 1.5f, center.y - r * 0.2f)
        lineTo(center.x, center.y + r * 1.6f)
        close()
    }
    drawPath(path = path, color = color)
}

private fun DrawScope.drawStar(center: Offset, size: Float, color: Color) {
    // 4-point sparkle star
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(center.x, center.y - size)
        quadraticTo(center.x, center.y, center.x + size, center.y)
        quadraticTo(center.x, center.y, center.x, center.y + size)
        quadraticTo(center.x, center.y, center.x - size, center.y)
        quadraticTo(center.x, center.y, center.x, center.y - size)
        close()
    }
    drawPath(path = path, color = color)
}
