package com.carlosdiestro.brokeless.onboarding.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    @DrawableRes leftIconId: Int? = R.drawable.ic_chevron_left,
    @StringRes leftTextId: Int = R.string.action_back,
    @DrawableRes rightIconId: Int? = R.drawable.ic_chevron_right,
    @StringRes rightTextId: Int = R.string.action_next,
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
            leftIconId = leftIconId,
            textId = leftTextId,
            isColorPrimary = false
        ) {
            onBackClicked()
        }
        BrokelessButton(
            textId = rightTextId,
            rightIconId = rightIconId,
            enabled = enabled
        ) {
            onNextClicked()
        }
    }
}