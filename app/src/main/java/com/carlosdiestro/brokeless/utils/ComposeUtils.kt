package com.carlosdiestro.brokeless.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp

fun Modifier.brokelessContentStyle(): Modifier =
    composed {
        clip(
            RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            )
        )
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    }

fun Modifier.pagerAnimation(pageOffset: Float): Modifier =
    graphicsLayer {
        lerp(
            start = 0.75F,
            stop = 1F,
            fraction = 1F - pageOffset.coerceIn(0F, 1F)
        ).also { scale ->
            scaleX = scale
            scaleY = scale
        }

        alpha = lerp(
            start = 0.5f,
            stop = 1f,
            fraction = 1f - pageOffset.coerceIn(0f, 1f)
        )
    }