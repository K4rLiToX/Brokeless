package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat


@Composable
fun CurrencyCard(
    modifier: Modifier = Modifier,
    currency: CurrencyPLO,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = currency.iconId), contentDescription = "Currency Flag")
        Text(
            text = "${currency.name} ${currency.symbol}",
            style = TextStyle(
                fontSize = 28.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}