package com.carlosdiestro.brokeless.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButton
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButtonResource
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun BrokelessTopBar(
    modifier: Modifier = Modifier,
    @DrawableRes leadIconId: Int? = null,
    @StringRes titleId: Int? = null,
    @DrawableRes trailIconId: Int? = null,
    onLeadIconClick: () -> Unit = {},
    onTrailIconClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadIconId != null) {
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.IconResource(leadIconId),
                size = BrokelessIconContainerSize.Small,
                containerColor = MaterialTheme.colorScheme.surface,
                onClick = onLeadIconClick
            )
        }
        if (titleId != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = titleId),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                ),
                textAlign = TextAlign.Center
            )
        }
        if (trailIconId != null) {
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.IconResource(trailIconId),
                size = BrokelessIconContainerSize.Small,
                onClick = onTrailIconClick
            )
        }
    }
}