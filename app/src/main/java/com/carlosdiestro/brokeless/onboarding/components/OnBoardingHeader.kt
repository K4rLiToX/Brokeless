package com.carlosdiestro.brokeless.onboarding.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun OnBoardingHeader(
    modifier: Modifier = Modifier,
    @StringRes textId: Int
) {
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = stringResource(id = textId),
        style = TextStyle(
            fontSize = 32.sp,
            fontFamily = Montserrat,
            fontWeight = FontWeight.Medium,
        ),
        maxLines = 3
    )
}