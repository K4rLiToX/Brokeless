package com.carlosdiestro.brokeless.main.budget.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
import com.carlosdiestro.brokeless.core.ui.components.BrokelessQuantity
import com.carlosdiestro.brokeless.core.ui.components.BrokelessTopBar
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButton
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconButtonResource
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessIconContainerSize
import com.carlosdiestro.brokeless.core.ui.components.cards.TransactionCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.main.budget.ui.models.BudgetPLO
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.utils.brokelessContentStyle
import com.carlosdiestro.brokeless.utils.directProportion
import com.carlosdiestro.brokeless.utils.round

@Composable
fun BudgetScreen(
    navController: NavController,
    viewModel: BudgetViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val budget = state.value.budget
    val budgetColorState = state.value.budgetColorState
    val lastTransactions = state.value.lastTransactions

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, budgetSection, actionsSection, recentTransactionsSection) = createRefs()

        BrokelessTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            titleId = R.string.section_budget,
        )

        BudgetProgress(
            modifier = Modifier
                .constrainAs(budgetSection) {
                    start.linkTo(parent.start)
                    top.linkTo(topBar.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                },
            budget = budget,
            budgetColorState = budgetColorState,
            currency = currency
        )

        BudgetActions(
            modifier = Modifier
                .constrainAs(actionsSection) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(budgetSection.bottom, margin = 4.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    width = Dimension.fillToConstraints
                },
            onAddClick = {
                navController.navigate("${NavigationDirections.Main.newTransactions.destination}/false")
            },
            onNewMonthClick = {
                viewModel.onEvent(BudgetEvent.NewPeriod)
            },
            onPayClick = {
                navController.navigate("${NavigationDirections.Main.newTransactions.destination}/true")
            }
        )

        LastTransactions(
            modifier = Modifier
                .constrainAs(recentTransactionsSection) {
                    start.linkTo(parent.start)
                    top.linkTo(actionsSection.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            lastTransactions = lastTransactions,
            currency = currency!!
        ) {
            navController.navigate(NavigationDirections.Main.transactions.destination)
        }
    }
}

@Composable
fun BudgetProgress(
    modifier: Modifier = Modifier,
    budget: BudgetPLO,
    budgetColorState: Color,
    currency: CurrencyPLO?
) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) (budget.current directProportion budget.total).toFloat() else 0F,
        animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(240.dp)) {
            drawArc(
                color = budgetColorState,
                startAngle = -220F,
                sweepAngle = 258 * currentPercentage.value,
                useCenter = false,
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        currency?.let {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                BrokelessQuantity(
                    quantity = "${budget.current.round(2)}",
                    currency = it,
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.text_of),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    BrokelessQuantity(
                        quantity = "${budget.total.round(2)}",
                        currency = it,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun BudgetActions(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit,
    onNewMonthClick: () -> Unit,
    onPayClick: () -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BrokelessIconButton(
            resource = BrokelessIconButtonResource.IconResource(R.drawable.ic_add),
            textId = R.string.action_add,
            size = BrokelessIconContainerSize.Medium
        ) {
            onAddClick()
        }

        BrokelessIconButton(
            resource = BrokelessIconButtonResource.IconResource(R.drawable.ic_credit),
            textId = R.string.action_new_month,
            size = BrokelessIconContainerSize.Medium
        ) {
            onNewMonthClick()
        }

        BrokelessIconButton(
            resource = BrokelessIconButtonResource.IconResource(R.drawable.ic_pay),
            textId = R.string.action_pay,
            size = BrokelessIconContainerSize.Medium
        ) {
            onPayClick()
        }
    }
}

@Composable
fun LastTransactions(
    modifier: Modifier = Modifier,
    lastTransactions: List<TransactionPLO>,
    currency: CurrencyPLO,
    onSeeMoreClick: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.brokelessContentStyle()
    ) {
        val (titleSection, transactionsSection) = createRefs()

        Row(
            modifier = Modifier
                .constrainAs(titleSection) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.subsection_budget_recent_transactions),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            )
            BrokelessIconButton(
                resource = BrokelessIconButtonResource.IconResource(R.drawable.ic_chevron_right),
                containerColor = MaterialTheme.colorScheme.surface,
                size = BrokelessIconContainerSize.Small,
                onClick = onSeeMoreClick
            )
        }

        LazyColumn(
            modifier = Modifier
                .constrainAs(transactionsSection) {
                    start.linkTo(parent.start)
                    top.linkTo(titleSection.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                },
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(lastTransactions) { transaction ->
                TransactionCard(
                    transaction = transaction,
                    currency = currency
                )
            }
        }
    }
}