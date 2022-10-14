package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.components.BrokelessIcon
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.core.ui.theme.White

@Composable
fun WalletCard(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes title: Int,
    quantity: Double,
    currency: CurrencyPLO,
    lightColor: Color,
    mediumColor: Color,
    darkColor: Color,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = lightColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        BrokelessIcon(
            iconId = iconId,
            size = BrokelessIconContainerSize.Small,
            containerColor = White
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(id = title),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            )
        )
        val quantityText =
            if (currency.goesFirst) "${currency.symbol}$quantity" else "$quantity${currency.symbol}"
        Text(
            text = quantityText,
            style = TextStyle(
                fontSize = 20.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            )
        )
    }
}