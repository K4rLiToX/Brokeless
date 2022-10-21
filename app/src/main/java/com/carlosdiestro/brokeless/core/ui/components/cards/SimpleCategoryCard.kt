package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SimpleCategoryCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100F))
            .background(MaterialTheme.colorScheme.primary)
            .size(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = iconId),
            contentDescription = "Category Icon"
        )
    }
}