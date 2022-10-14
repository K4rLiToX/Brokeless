package com.carlosdiestro.brokeless.core.ui.components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun BrokelessButton(
    modifier: Modifier = Modifier,
    @DrawableRes leftIconId: Int? = null,
    @StringRes textId: Int,
    @DrawableRes rightIconId: Int? = null,
    isColorPrimary: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {

    Button(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isColorPrimary) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            contentColor = if (isColorPrimary) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
        ),
        enabled = enabled
    ) {
        if (leftIconId != null) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = leftIconId),
                contentDescription = "Left Icon",
                tint = if (isColorPrimary) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
        }
        Text(
            text = stringResource(id = textId),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium,
                lineHeight = 32.sp
            )
        )
        if (rightIconId != null) {
            Spacer(modifier = modifier.width(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = rightIconId),
                contentDescription = "Right Icon",
                tint = if (isColorPrimary) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}