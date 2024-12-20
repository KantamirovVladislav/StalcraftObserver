package com.example.stalcraftobserver.presentation.common

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerPlaceholder(
    modifier: Modifier = Modifier,
    baseColor: Color = Color.LightGray.copy(alpha = 0.6f),
    highlightColor: Color = Color.LightGray.copy(alpha = 0.2f)
) {
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        ), label = "kekw"
    )

    val brush = Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(translateAnim - 1000f, 0f),
        end = Offset(translateAnim, 0f)
    )

    Box(
        modifier = modifier
            .background(brush)
    )
}
