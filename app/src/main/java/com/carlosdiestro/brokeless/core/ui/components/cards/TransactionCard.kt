package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.carlosdiestro.brokeless.budget.ui.TransactionPLO
import com.carlosdiestro.brokeless.core.ui.components.BrokelessIcon
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun TransactionCard(
    modifier: Modifier,
    transaction: TransactionPLO,
) {

    ConstraintLayout(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxWidth()
    ) {
        val (category, conceptDescription, dateQuantity) = createRefs()

        BrokelessIcon(
            modifier = Modifier
                .constrainAs(category) {
                    start.linkTo(parent.start)
                    top.linkTo(conceptDescription.top)
                    bottom.linkTo(conceptDescription.bottom)
                },
            iconId = transaction.category.iconId,
            size = BrokelessIconContainerSize.Small
        )

        Column(
            modifier = Modifier
                .constrainAs(conceptDescription) {
                    start.linkTo(category.end, margin = 16.dp)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(dateQuantity.start)
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = transaction.concept,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            if (transaction.description.isNotEmpty()) {
                Text(
                    text = transaction.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }
        }

        Column(
            modifier = Modifier
                .constrainAs(dateQuantity) {
                    start.linkTo(conceptDescription.end, margin = 4.dp)
                    top.linkTo(conceptDescription.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(conceptDescription.bottom)
                    width = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = transaction.date,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.End
            )
            Text(
                text = transaction.quantityText,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Medium,
                    color = transaction.colorState
                ),
                textAlign = TextAlign.End
            )
        }
    }
}