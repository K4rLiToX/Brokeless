package com.carlosdiestro.brokeless.main.statistics.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.ui.components.BrokelessQuantity
import com.carlosdiestro.brokeless.core.ui.components.BrokelessTopBar
import com.carlosdiestro.brokeless.core.ui.components.cards.CategoryCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val categories = state.value.categories
    val periodExpenses = state.value.periodExpenses
    val periodIncomes = state.value.periodIncomes
    val period = state.value.period

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topBar, periodSection, categoriesSection) = createRefs()

        BrokelessTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            titleId = R.string.section_statistics
        )
        if (currency != null && period != null) {
            PeriodSection(
                modifier = Modifier
                    .constrainAs(periodSection) {
                        start.linkTo(parent.start, margin = 24.dp)
                        top.linkTo(topBar.bottom, margin = 24.dp)
                        end.linkTo(parent.end, margin = 24.dp)
                        width = Dimension.fillToConstraints
                    },
                currency = currency,
                periodExpenses = periodExpenses,
                periodIncomes = periodIncomes,
                period = period
            )
        }
        Column(
            modifier = Modifier
                .constrainAs(categoriesSection) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(periodSection.bottom, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.subsection_statistics_by_categories),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                items(categories) { category ->
                    currency?.let {
                        CategoryCard(
                            category = category.category,
                            currency = it,
                            spent = category.balance,
                            isStatisticsScreen = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PeriodSection(
    modifier: Modifier = Modifier,
    currency: CurrencyPLO,
    periodExpenses: Double,
    periodIncomes: Double,
    period: PeriodPLO
) {

    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            text = "${period.simpleStartDate} - ${period.simpleEndDate}",
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.48F),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.action_spent),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                BrokelessQuantity(
                    quantity = periodExpenses.toString(), currency = currency, style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(id = R.string.action_received),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                BrokelessQuantity(
                    quantity = periodIncomes.toString(), currency = currency, style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}