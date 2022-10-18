package com.carlosdiestro.brokeless.core.ui.components


import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO

@Composable
fun BrokelessQuantity(
    modifier: Modifier =  Modifier,
    quantity: String,
    currency: CurrencyPLO,
    style: TextStyle
) {

    val quantityText = if (currency.goesFirst) "${currency.symbol}$quantity" else "$quantity${currency.symbol}"

    Text(
        modifier = modifier.wrapContentHeight(),
        text = quantityText,
        style = style
    )
}