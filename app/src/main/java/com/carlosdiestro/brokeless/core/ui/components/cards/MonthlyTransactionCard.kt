package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.carlosdiestro.brokeless.core.ui.components.BrokelessIcon
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.core.ui.theme.White
import com.carlosdiestro.brokeless.wallet.ui.models.MonthlyTransactionPLO

@Composable
fun MonthlyTransactionCard(
    modifier: Modifier = Modifier,
    monthlyTransaction: MonthlyTransactionPLO,
    isFilled: Boolean = false
) {

    val cardModifier = if (isFilled) modifier
        .clip(MaterialTheme.shapes.medium)
        .background(White)
        .padding(16.dp)
        .fillMaxWidth()
    else
        modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()

    ConstraintLayout(
        modifier = cardModifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        val (category, concept, quantity) = createRefs()

        BrokelessIcon(
            modifier = Modifier
                .constrainAs(category) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            iconId = monthlyTransaction.category.iconId,
            size = BrokelessIconContainerSize.Small
        )

        Text(
            modifier = Modifier
                .constrainAs(concept) {
                    start.linkTo(category.end, margin = 16.dp)
                    top.linkTo(category.top)
                    bottom.linkTo(category.bottom)
                },
            text = monthlyTransaction.concept,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Text(
            modifier = Modifier
                .constrainAs(quantity) {
                    start.linkTo(concept.end, margin = 16.dp)
                    top.linkTo(concept.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(concept.bottom)
                    width = Dimension.fillToConstraints
                },
            text = monthlyTransaction.quantityText,
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            ),
            textAlign = TextAlign.End
        )
    }
}