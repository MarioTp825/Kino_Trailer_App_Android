package com.tepe.mymovie.ui.modifiers

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.skeleton(
    baseColor: Color = Color.LightGray.copy(alpha = 0.3f),
    highlightColor: Color = Color.White.copy(alpha = 0.6f),
) = composed {
    val shimmerColors = listOf(
        baseColor,
        highlightColor,
        baseColor
    )

    val transition = rememberInfiniteTransition(label = "skeleton_transition")
    val translateAnimation by transition.animateFloat(
        initialValue = -2000f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = "skeleton_animation"
    )

    background(baseColor)
        .drawWithContent {
            drawContent()
            drawRect(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(translateAnimation, 0f),
                    end = Offset(translateAnimation + size.width, size.height)
                ),
                size = this.size,
            )
        }
}