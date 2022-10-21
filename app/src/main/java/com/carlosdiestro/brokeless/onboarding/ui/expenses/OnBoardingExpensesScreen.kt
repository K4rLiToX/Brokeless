package com.carlosdiestro.brokeless.onboarding.ui.expenses

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.clip
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
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingButtons
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingHeader
import com.carlosdiestro.brokeless.utils.expenses
import com.carlosdiestro.brokeless.utils.total

@Composable
fun OnBoardingExpensesScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel
) {
    val onBoardingState = onBoardingViewModel.state.collectAsState()
    val currency = onBoardingState.value.selectedCurrency
    val expenses = onBoardingState.value.monthlyTransactions.expenses()
    val total = expenses.total()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, expenseTotal, content) = createRefs()

        OnBoardingHeader(
            modifier = Modifier
                .constrainAs(header) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 40.dp)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 24.dp),
            textId = R.string.header_on_boarding_expenses
        )

        BrokelessQuantity(
            modifier = Modifier
                .constrainAs(expenseTotal) {
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

        OnBoardingExpensesContent(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(expenseTotal.bottom, margin = 36.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            expenses = expenses,
            currency = currency,
            onBackClick = {
                navController.popBackStack()
            },
            onNextClick = {
                navController.navigate(NavigationDirections.OnBoarding.savings.destination)
            },
            onAddClick = {
                navController.navigate("${NavigationDirections.OnBoarding.newTransaction.destination}/true")
            }
        )
    }
}

@Composable
fun OnBoardingExpensesContent(
    modifier: Modifier = Modifier,
    expenses: List<MonthlyTransactionPLO>,
    currency: CurrencyPLO,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onAddClick: () -> Unit
) {

    ConstraintLayout(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp
                )
            )
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        val (expenseList, newExpense, buttons) = createRefs()

        ExpenseList(
            modifier = Modifier
                .constrainAs(expenseList) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttons.top, margin = 32.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            items = expenses,
            currency = currency
        )

        FloatingActionButton(
            modifier = Modifier
                .constrainAs(newExpense) {
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
                contentDescription = "New Income"
            )
        }

        OnBoardingButtons(
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            enabled = expenses.isNotEmpty(),
            onBackClicked = onBackClick,
            onNextClicked = onNextClick
        )
    }
}

@Composable
fun ExpenseList(
    modifier: Modifier = Modifier,
    items: List<MonthlyTransactionPLO>,
    currency: CurrencyPLO
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(items) { expense ->
            MonthlyTransactionCard(
                iconId = expense.category.iconId,
                concept = expense.concept,
                quantity = expense.quantity,
                currency = currency
            )
        }
    }
}