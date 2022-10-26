package com.carlosdiestro.brokeless.main.wallet.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.components.BrokelessTopBar
import com.carlosdiestro.brokeless.core.ui.components.buttons.ButtonTabs
import com.carlosdiestro.brokeless.core.ui.components.cards.CategoryCard
import com.carlosdiestro.brokeless.core.ui.components.cards.MonthlyTransactionCard
import com.carlosdiestro.brokeless.core.ui.components.cards.WalletCard
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.*

@Composable
fun WalletScreen(
    navController: NavController,
    viewModel: WalletViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val balance = state.value.balance
    val available = state.value.available
    val savings = state.value.savings
    val incomes = state.value.incomes
    val expenses = state.value.expenses
    val categories = state.value.categories
    val page = state.value.page

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        val (topBar, tabSection, overviewSection, categorySection) = createRefs()

        BrokelessTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            titleId = R.string.section_wallet
        )

        ButtonTabs(
            modifier = Modifier
                .constrainAs(tabSection) {
                    start.linkTo(parent.start)
                    top.linkTo(topBar.bottom)
                    end.linkTo(parent.end)
                },
            currentTab = page,
            titleIdFirst = R.string.action_overview,
            titleIdSecond = R.string.action_categories,
            inactiveColor = MaterialTheme.colorScheme.background
        ) {
            viewModel.onEvent(WalletEvent.ChangePage)
        }

        if (page == 0) {
            WalletContentOverview(
                modifier = Modifier
                    .constrainAs(overviewSection) {
                        start.linkTo(parent.start)
                        top.linkTo(tabSection.bottom, margin = 24.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                currency = currency,
                balance = balance,
                available = available,
                savings = savings,
                incomes = incomes,
                expenses = expenses,
                onAvailableClick = {},
                onSavingsClick = {},
                onMonthlyTransactionsClick = {
                    navController.navigate(
                        "${NavigationDirections.Main.monthlyTransactions.destination}/$it"
                    )
                }
            )
        } else {
            WalletContentCategories(
                modifier = Modifier
                    .constrainAs(categorySection) {
                        start.linkTo(parent.start)
                        top.linkTo(tabSection.bottom, margin = 24.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                currency = currency,
                categories = categories
            )
        }
    }
}

@Composable
fun WalletContentOverview(
    modifier: Modifier = Modifier,
    currency: CurrencyPLO?,
    balance: Double,
    available: Double,
    savings: Double,
    incomes: Double,
    expenses: Double,
    onAvailableClick: () -> Unit = {},
    onSavingsClick: () -> Unit = {},
    onMonthlyTransactionsClick: (Int) -> Unit = {}
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (balanceSection, totalSection, monthlyTitle, monthlySection) = createRefs()
        currency?.let {
            MonthlyTransactionCard(
                modifier = Modifier
                    .constrainAs(balanceSection) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                iconId = R.drawable.ic_balance,
                concept = stringResource(id = R.string.title_balance),
                quantity = balance,
                currency = it,
                isFilled = true
            )
        }

        currency?.let {
            Row(
                modifier = Modifier
                    .constrainAs(totalSection) {
                        start.linkTo(parent.start)
                        top.linkTo(balanceSection.bottom, margin = 24.dp)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WalletCard(
                    modifier = Modifier.fillMaxWidth(0.46F),
                    iconId = R.drawable.ic_credit,
                    title = R.string.title_available,
                    quantity = available,
                    currency = it,
                    lightColor = Blue20,
                    mediumColor = Blue30,
                    darkColor = Blue40,
                    isLarge = false
                ) { onAvailableClick() }
                WalletCard(
                    modifier = Modifier.fillMaxWidth(),
                    iconId = R.drawable.ic_savings,
                    title = R.string.title_savings,
                    quantity = savings,
                    currency = it,
                    lightColor = Red30,
                    mediumColor = Red40,
                    darkColor = Red50,
                    isLarge = false
                ) { onSavingsClick() }
            }
        }

        Text(
            modifier = Modifier
                .constrainAs(monthlyTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(totalSection.bottom, margin = 40.dp)
                },
            text = stringResource(id = R.string.subsection_wallet_monthly),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )

        currency?.let {
            Row(
                modifier = Modifier
                    .constrainAs(monthlySection) {
                        start.linkTo(parent.start)
                        top.linkTo(monthlyTitle.bottom, margin = 16.dp)
                        end.linkTo(parent.end)
                    },
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                WalletCard(
                    modifier = Modifier.fillMaxWidth(0.46F),
                    iconId = R.drawable.ic_incomes,
                    title = R.string.title_incomes,
                    quantity = incomes,
                    currency = it,
                    lightColor = Purple10,
                    mediumColor = Purple20,
                    darkColor = Purple30,
                    isLarge = false
                ) { onMonthlyTransactionsClick(0) }
                WalletCard(
                    modifier = Modifier.fillMaxWidth(),
                    iconId = R.drawable.ic_expenses,
                    title = R.string.title_expenses,
                    quantity = expenses,
                    currency = it,
                    lightColor = Orange10,
                    mediumColor = Orange30,
                    darkColor = Orange40,
                    isLarge = false
                ) { onMonthlyTransactionsClick(1) }
            }
        }
    }
}

@Composable
fun WalletContentCategories(
    modifier: Modifier = Modifier,
    currency: CurrencyPLO?,
    categories: List<CategoryPLO>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(categories) { category ->
            currency?.let {
                CategoryCard(category = category, currency = it)
            }
        }
    }
}