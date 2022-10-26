package com.carlosdiestro.brokeless.main.wallet.ui.monthly_transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.ui.components.BrokelessTopBar
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessButton
import com.carlosdiestro.brokeless.core.ui.components.cards.MonthlyTransactionCard
import com.carlosdiestro.brokeless.core.ui.components.cards.WalletCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.*
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.incomes
import com.carlosdiestro.brokeless.utils.total
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MonthlyTransactionsScreen(
    navController: NavController,
    viewModel: MonthlyTransactionsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val currentPage = state.value.page
    val transactions = state.value.transactions

    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = transactions) {
        pagerState.scrollToPage(currentPage)
        snapshotFlow { pagerState.currentPage }.collect {
            viewModel.onEvent(MonthlyTransactionsEvent.ChangePage)
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topBar, cardSection, transactionsList, button) = createRefs()

        BrokelessTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            leadIconId = R.drawable.ic_back_arrow,
            onLeadIconClick = {
                navController.popBackStack()
            }
        )

        MonthlyCardPager(
            modifier = Modifier
                .constrainAs(cardSection) {
                    start.linkTo(parent.start)
                    top.linkTo(topBar.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                }
                .padding(start = 24.dp, end = 24.dp),
            state = pagerState,
            currency = currency,
            transactions = if (currentPage == 0) transactions.incomes() else transactions.expenses(),
            currentPage = currentPage,
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(transactionsList) {
                    start.linkTo(parent.start)
                    top.linkTo(cardSection.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .padding(start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(if (currentPage == 0) transactions.incomes() else transactions.expenses()) { transaction ->
                currency?.let {
                    MonthlyTransactionCard(
                        iconId = transaction.category.iconId,
                        concept = transaction.concept,
                        quantity = transaction.quantity,
                        currency = it,
                        isFilled = true
                    )
                }
            }
        }

        BrokelessButton(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    bottom.linkTo(parent.bottom, margin = 24.dp)
                    width = Dimension.fillToConstraints
                },
            textId = R.string.action_add,
            leftIconId = R.drawable.ic_add,
        ) {
            // TODO(Navigate to new monthly transaction screen)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MonthlyCardPager(
    modifier: Modifier = Modifier,
    state: PagerState,
    currency: CurrencyPLO?,
    transactions: List<MonthlyTransactionPLO>,
    currentPage: Int
) {

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            itemSpacing = 12.dp,
            count = 2
        ) {
            currency?.let {
                WalletCard(
                    iconId = if (currentPage == 0) R.drawable.ic_incomes else R.drawable.ic_expenses,
                    title = if (currentPage == 0) R.string.title_incomes else R.string.title_expenses,
                    quantity = transactions.total(),
                    currency = it,
                    lightColor = if (currentPage == 0) Purple10 else Orange10,
                    mediumColor = if (currentPage == 0) Purple20 else Orange30,
                    darkColor = if (currentPage == 0) Purple30 else Orange40,
                    isLarge = true
                )
            }
        }
        HorizontalPagerIndicator(
            pagerState = state,
            pageCount = 2,
            activeColor = Blue40,
            inactiveColor = MaterialTheme.colorScheme.primary
        )
    }
}