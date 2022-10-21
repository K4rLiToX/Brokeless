package com.carlosdiestro.brokeless.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessButton

@Composable
fun OnBoardingButtons(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally)
    ) {
        BrokelessButton(
            fraction = 0.48F,
            leftIconId = R.drawable.ic_chevron_left,
            textId = R.string.action_back,
            isColorPrimary = false
        ) {
            onBackClicked()
        }
        BrokelessButton(
            textId = R.string.action_next,
            rightIconId = R.drawable.ic_chevron_right,
            enabled = enabled
        ) {
            onNextClicked()
        }
    }
}