package com.carlosdiestro.brokeless.core.ui.components.buttons

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun ButtonTabs(
    modifier: Modifier = Modifier,
    currentTab: Int,
    @StringRes titleIdFirst: Int,
    @StringRes titleIdSecond: Int,
    inactiveColor: Color = MaterialTheme.colorScheme.surface,
    onTabClick: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.48F),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentTab == 0) MaterialTheme.colorScheme.primary else inactiveColor,
                contentColor = if (currentTab == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            onClick = onTabClick
        ) {
            Text(
                text = stringResource(id = titleIdFirst),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = if (currentTab == 0) FontWeight.SemiBold else FontWeight.Medium
                )
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentTab == 0) inactiveColor else MaterialTheme.colorScheme.primary,
                contentColor = if (currentTab == 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
            ),
            onClick = onTabClick
        ) {
            Text(
                text = stringResource(id = titleIdSecond),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = if (currentTab == 0) FontWeight.Medium else FontWeight.SemiBold
                )
            )
        }
    }
}