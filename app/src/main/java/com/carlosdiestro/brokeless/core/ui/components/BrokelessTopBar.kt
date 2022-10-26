package com.carlosdiestro.brokeless.core.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrokelessTopBar(
    modifier: Modifier = Modifier,
    @DrawableRes leadIconId: Int? = null,
    @StringRes titleId: Int? = null,
    @DrawableRes trailIconId: Int? = null,
    onLeadIconClick: () -> Unit = {},
    onTrailIconClick: () -> Unit = {}
) {

    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = {
            if (leadIconId != null) {
                BrokelessIconButton(
                    resource = BrokelessIconButtonResource.IconResource(leadIconId),
                    size = BrokelessIconContainerSize.Small,
                    containerColor = MaterialTheme.colorScheme.surface,
                    onClick = onLeadIconClick
                )
            }
        },
        title = {
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
        },
        actions = {
            if (trailIconId != null) {
                BrokelessIconButton(
                    resource = BrokelessIconButtonResource.IconResource(trailIconId),
                    size = BrokelessIconContainerSize.Small,
                    onClick = onTrailIconClick
                )
            }
        }
    )
}