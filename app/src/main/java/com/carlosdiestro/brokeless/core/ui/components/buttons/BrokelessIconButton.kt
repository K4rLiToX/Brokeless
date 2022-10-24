package com.carlosdiestro.brokeless.core.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun BrokelessIconButton(
    modifier: Modifier = Modifier,
    resource: BrokelessIconButtonResource,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    @StringRes textId: Int? = null,
    size: BrokelessIconContainerSize,
    onClick: () -> Unit = {}
) {

    val finalModifier = if (textId == null) modifier.wrapContentSize().clip(CircleShape) else modifier.wrapContentSize()

    Column(
        modifier = finalModifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        IconButton(
            modifier = Modifier.size(size.size).clip(CircleShape),
            onClick = { onClick() },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            when (resource) {
                is BrokelessIconButtonResource.IconResource -> {
                    Icon(
                        painter = painterResource(id = resource.iconId),
                        contentDescription = "Icon Button"
                    )
                }
                is BrokelessIconButtonResource.TextResource -> {
                    Text(
                        text = resource.text,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 32.sp
                        )
                    )
                }
            }
        }
        if (textId != null) {
            Text(
                text = stringResource(id = textId),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 32.sp
                )
            )
        }
    }
}

enum class BrokelessIconContainerSize(val size: Dp) {
    Small(42.dp),
    Medium(56.dp),
    Large(80.dp),
    ExtraLarge(120.dp)
}

sealed interface BrokelessIconButtonResource {
    class IconResource(@DrawableRes val iconId: Int) : BrokelessIconButtonResource
    class TextResource(val text: String) : BrokelessIconButtonResource
}