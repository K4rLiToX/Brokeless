package com.carlosdiestro.brokeless.onboarding.ui.monthly_transactions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.components.BrokelessQuantity
import com.carlosdiestro.brokeless.core.ui.components.cards.MonthlyTransactionCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.onboarding.OnBoardingEvent
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingButtons
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingHeader
import com.carlosdiestro.brokeless.utils.brokelessContentStyle
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.incomes
import com.carlosdiestro.brokeless.utils.total

@Composable
fun OnBoardingMonthlyTransactionsScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel,
    isIncome: Boolean = true
) {
    val onBoardingState = onBoardingViewModel.state.collectAsState()
    val currency = onBoardingState.value.selectedCurrency
    val monthlyTransactions =
        if (!isIncome)
            onBoardingState.value.monthlyTransactions.expenses()
        else
            onBoardingState.value.monthlyTransactions.incomes()
    val total = monthlyTransactions.total()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, monthlyTransactionsTotal, content) = createRefs()

        OnBoardingHeader(
            modifier = Modifier
                .constrainAs(header) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 40.dp)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 24.dp),
            textId = if (!isIncome) R.string.header_on_boarding_expenses else R.string.header_on_boarding_incomes
        )

        BrokelessQuantity(
            modifier = Modifier
                .constrainAs(monthlyTransactionsTotal) {
                    start.linkTo(parent.start)
                    top.linkTo(header.bottom, margin = 52.dp)
                    end.linkTo(parent.end)
                },
            quantity = total.toString(),
            currency = currency!!,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            )
        )

        OnBoardingMonthlyTransactionsContent(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(monthlyTransactionsTotal.bottom, margin = 36.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            monthlyTransactions = monthlyTransactions,
            currency = currency,
            onBackClick = {
                navController.popBackStack()
            },
            onNextClick = {
                if (!isIncome) {
                    onBoardingViewModel.onEvent(OnBoardingEvent.SubmitMonthlyTransactions)
                    navController.navigate(NavigationDirections.OnBoarding.savings.destination)
                } else {
                    navController.navigate(
                        "${NavigationDirections.OnBoarding.monthlyTransactions.destination}/false"
                    )
                }
            },
            onAddClick = {
                navController.navigate("${NavigationDirections.OnBoarding.newTransaction.destination}/${!isIncome}")
            }
        )
    }
}

@Composable
fun OnBoardingMonthlyTransactionsContent(
    modifier: Modifier = Modifier,
    monthlyTransactions: List<MonthlyTransactionPLO>,
    currency: CurrencyPLO,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onAddClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier.brokelessContentStyle()
    ) {
        val (monthlyTransactionsList, newMonthlyTransaction, buttons) = createRefs()

        MonthlyTransactionsList(
            modifier = Modifier
                .constrainAs(monthlyTransactionsList) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttons.top, margin = 32.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            items = monthlyTransactions,
            currency = currency
        )

        FloatingActionButton(
            modifier = Modifier
                .constrainAs(newMonthlyTransaction) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttons.top, margin = 56.dp)
                },
            onClick = onAddClick,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(100)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "New Monthly Transaction"
            )
        }

        OnBoardingButtons(
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            enabled = monthlyTransactions.isNotEmpty(),
            onBackClicked = onBackClick,
            onNextClicked = onNextClick
        )
    }
}

@Composable
fun MonthlyTransactionsList(
    modifier: Modifier = Modifier,
    items: List<MonthlyTransactionPLO>,
    currency: CurrencyPLO
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items) { monthlyTransaction ->
            MonthlyTransactionCard(
                iconId = monthlyTransaction.category.iconId,
                concept = monthlyTransaction.concept,
                quantity = monthlyTransaction.quantity,
                currency = currency
            )
        }
    }
}