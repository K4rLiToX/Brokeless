package com.carlosdiestro.brokeless.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.theme.*
import com.carlosdiestro.brokeless.utils.asPercentage
import com.carlosdiestro.brokeless.utils.directProportion

@Composable
fun BrokelessIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    containerColor: Color = MaterialTheme.colorScheme.background,
    size: BrokelessIconContainerSize,
    progress: BrokelessIconProgress? = null
) {
    Box(
        modifier = modifier
            .background(containerColor)
            .clip(RoundedCornerShape(100))
            .size(size.size)
            .padding(9.dp),
        contentAlignment = Alignment.Center
    ) {
        if (progress != null) {
            Canvas(modifier = Modifier.size(size.size)) {
                drawArc(
                    color = progress.stateColor,
                    startAngle = -90F,
                    sweepAngle = (360 * progress.proportion).toFloat(),
                    useCenter = false,
                    style = Stroke(
                        width = 6.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Icon",
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

class BrokelessIconProgress(
    private val current: Double,
    private val total: Double,
) {
    val proportion: Double
        get() {
            return current directProportion total
        }
    val stateColor: Color
        get() {
            val percentage = proportion.asPercentage()
            return when {
                percentage > 100 -> Black
                percentage == 100.0 -> Red50
                percentage in (75.0..99.99) -> Red40
                percentage in (50.0..74.99) -> Orange30
                percentage in (0.0..49.99) -> Green
                else -> Black
            }
        }
}