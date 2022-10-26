package com.carlosdiestro.brokeless.main.transactions.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
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
import com.carlosdiestro.brokeless.core.ui.components.BrokelessTopBar
import com.carlosdiestro.brokeless.core.ui.components.cards.TransactionCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.main.transactions.ui.models.PeriodPLO
import com.carlosdiestro.brokeless.utils.brokelessContentStyle
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.incomes
import com.carlosdiestro.brokeless.utils.pagerAnimation
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TransactionsScreen(
    navController: NavController,
    viewModel: TransactionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val periods = state.value.periods
    val transactions = state.value.transactions
    val currency = state.value.currency
    val filter = state.value.filter
    val currentPeriod = state.value.currentPeriod

    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = periods) {
        if (currentPeriod != null) {
            pagerState.scrollToPage(
                periods.indexOf(
                    currentPeriod
                )
            )
        }
        snapshotFlow { pagerState.currentPage }.collect { index ->
            if (periods.isNotEmpty()) {
                viewModel.onEvent(
                    TransactionsEvent.UpdatePeriod(
                        periods[index]
                    )
                )
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, periodPager, content) = createRefs()

        BrokelessTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            leadIconId = R.drawable.ic_back_arrow,
            titleId = R.string.section_transactions,
            onLeadIconClick = {
                navController.popBackStack()
            }
        )

        PeriodPager(
            modifier = Modifier
                .constrainAs(periodPager) {
                    start.linkTo(parent.start)
                    top.linkTo(topBar.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            periods = periods,
            pagerState = pagerState
        )

        TransactionsContent(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(periodPager.bottom, margin = 56.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            transactions = if (filter == 0) transactions.expenses() else transactions.incomes(),
            currency = currency,
            filter = filter,
            onFiltersClick = {
                viewModel.onEvent(TransactionsEvent.ChangeFilter)
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PeriodPager(
    modifier: Modifier = Modifier,
    periods: List<PeriodPLO>,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = modifier.fillMaxWidth(),
        count = periods.size,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 100.dp)
    ) { index ->
        val period = periods[index]
        val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

        Text(
            modifier = Modifier
                .pagerAnimation(pageOffset)
                .fillMaxWidth(),
            text = "${period.startDate} - ${period.endDate}",
            style = TextStyle(
                fontSize = 28.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun TransactionsContent(
    modifier: Modifier = Modifier,
    transactions: List<TransactionPLO>,
    currency: CurrencyPLO?,
    filter: Int,
    onFiltersClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.brokelessContentStyle()
    ) {
        val (filterSection, transactionList) = createRefs()

        TransactionsFilter(
            modifier = Modifier
                .constrainAs(filterSection) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
            currentFilter = filter,
            onFiltersClick = onFiltersClick
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(transactionList) {
                    start.linkTo(parent.start)
                    top.linkTo(filterSection.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(transactions) { transaction ->
                currency?.let { TransactionCard(transaction = transaction, currency = it) }

            }
        }
    }
}

@Composable
fun TransactionsFilter(
    modifier: Modifier = Modifier,
    currentFilter: Int,
    onFiltersClick: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(0.48F),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentFilter == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                contentColor = if (currentFilter == 0) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            onClick = onFiltersClick
        ) {
            Text(
                text = stringResource(id = R.string.action_spent),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = if (currentFilter == 0) FontWeight.SemiBold else FontWeight.Medium
                )
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (currentFilter == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
                contentColor = if (currentFilter == 0) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary
            ),
            onClick = onFiltersClick
        ) {
            Text(
                text = stringResource(id = R.string.action_received),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = Montserrat,
                    fontWeight = if (currentFilter == 0) FontWeight.Medium else FontWeight.SemiBold
                )
            )
        }
    }
}

