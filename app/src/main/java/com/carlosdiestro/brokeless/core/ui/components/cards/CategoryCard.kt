package com.carlosdiestro.brokeless.core.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.ui.components.BrokelessIcon
import com.carlosdiestro.brokeless.core.ui.components.BrokelessIconProgress
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButton
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButtonResource
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.theme.*

@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: CategoryPLO,
    spent: Double?,
    differencePercentage: Int?,
    isStatisticsScreen: Boolean = false,
    onClick: (CategoryPLO) -> Unit = {}
) {

    ConstraintLayout(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(White)
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { onClick(category) }
    ) {
        val (categoryIcon, info, icon) = createRefs()

        BrokelessIcon(
            modifier = Modifier
                .constrainAs(categoryIcon) {
                    start.linkTo(parent.start)
                    top.linkTo(info.top)
                    bottom.linkTo(info.bottom)
                },
            iconId = category.iconId,
            size = BrokelessIconContainerSize.Small,
            progress = if (category.limit != null && spent != null && differencePercentage != null) BrokelessIconProgress(
                current = spent,
                total = category.limit
            ) else null
        )

        Column(
            modifier = Modifier
                .constrainAs(info) {
                    start.linkTo(categoryIcon.end, margin = 16.dp)
                    top.linkTo(parent.top)
                    end.linkTo(icon.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = category.textId),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val spentText =
                    if (category.currency.goesFirst) "${category.currency.symbol}$spent" else "$spent${category.currency.symbol}"
                val limitText =
                    if (category.currency.goesFirst) "${category.currency.symbol}${category.limit}" else "${category.limit}${category.currency.symbol}"
                Text(
                    text = if (isStatisticsScreen) spentText else limitText,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                if (isStatisticsScreen) {
                    val percentage =
                        if (differencePercentage!! >= 0) "+$differencePercentage" else "-$differencePercentage"
                    Text(
                        text = stringResource(id = R.string.placeholder_percentage, percentage),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.Normal,
                            color = if (differencePercentage >= 0) Green else Red40
                        )
                    )
                }
            }
        }
        BrokelessIconButton(
            modifier = Modifier
                .constrainAs(icon) {
                    top.linkTo(info.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(info.bottom)
                },
            resource = BrokelessIconButtonResource.IconResource(R.drawable.ic_chevron_right),
            size = BrokelessIconContainerSize.Small
        )
    }
}