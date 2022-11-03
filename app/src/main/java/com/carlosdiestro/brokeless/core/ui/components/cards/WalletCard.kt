package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.carlosdiestro.brokeless.core.ui.components.BrokelessQuantity
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
    isLarge: Boolean,
    onClick: () -> Unit = {}
) {

    //TODO(Use medium and dark colors for decoration )

    val cardModifier = if (isLarge) modifier
        .fillMaxWidth()
        .clickable { onClick() }
    else modifier.clickable { onClick() }

    Card(
        modifier = cardModifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = lightColor,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        BrokelessIcon(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            iconId = iconId,
            size = BrokelessIconContainerSize.Small,
            containerColor = White
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(id = title),
            style = TextStyle(
                fontSize = if (isLarge) 16.sp else 14.sp,
                fontFamily = Montserrat,
                fontWeight = if (isLarge) FontWeight.SemiBold else FontWeight.Medium
            )
        )

        BrokelessQuantity(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            quantity = quantity.toString(),
            currency = currency,
            style = TextStyle(
                fontSize = if (isLarge) 40.sp else 20.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            )
        )
    }
}