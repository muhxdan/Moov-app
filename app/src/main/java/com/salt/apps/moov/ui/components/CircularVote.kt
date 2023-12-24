package com.salt.apps.moov.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularVote(
    percentage: Float,
    radius: Dp = 15.dp,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    strokeWidth: Dp = 2.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        val circleBgColor = MaterialTheme.colorScheme.scrim
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawCircle(
                color = circleBgColor,
                alpha = 0.6f
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * (currentPercentage.value / 10),
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = "%.1f".format(currentPercentage.value),
            color = MaterialTheme.colorScheme.surfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
    }
}