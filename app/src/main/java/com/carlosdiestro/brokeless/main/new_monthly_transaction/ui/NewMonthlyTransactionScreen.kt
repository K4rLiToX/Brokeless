package com.carlosdiestro.brokeless.main.new_monthly_transaction.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
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
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.NewTransactionContentFirst
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.NewTransactionContentSecond
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewMonthlyTransactionScreen(
    navController: NavController,
    viewModel: NewMonthlyTransactionViewModel = hiltViewModel(),
    isExpense: Boolean = false
) {

    val state = viewModel.state.collectAsState()

    val currency = state.value.currency
    val currentPage = state.value.currentPage
    val categories = state.value.categories
    val selectedCategory = state.value.selectedCategory
    val totalQuantity = state.value.quantity
    val concept = state.value.concept
    val description = state.value.description
    val isQuantityValid = state.value.isTotalQuantityValid
    val isAdditionalInfoValid = state.value.isAdditionalInfoValid

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (topBar, quantity, content) = createRefs()

        BrokelessTopBar(
            modifier = Modifier
                .constrainAs(topBar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            titleId = if (isExpense) R.string.section_new_expense else R.string.section_new_income,
            leadIconId = R.drawable.ic_back_arrow,
            onLeadIconClick = {
                if (currentPage == 1) navController.popBackStack()
                else viewModel.onEvent(NewMonthlyTransactionEvent.ChangePage)
            }
        )

        BrokelessQuantity(
            modifier = Modifier
                .constrainAs(quantity) {
                    start.linkTo(parent.start)
                    top.linkTo(topBar.bottom, margin = 60.dp)
                    end.linkTo(parent.end)
                },
            quantity = totalQuantity,
            currency = currency!!,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            )
        )

        if (currentPage == 1) {

            NewTransactionContentFirst(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(parent.start)
                        top.linkTo(quantity.bottom, margin = 60.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    },
                isContinueEnabled = isQuantityValid,
                onContinueClick = {
                    viewModel.onEvent(NewMonthlyTransactionEvent.ChangePage)
                },
                onKeyClicked = {
                    viewModel.onEvent(NewMonthlyTransactionEvent.UpdateQuantity(it))
                }
            )
        } else {

            val pagerState = rememberPagerState()

            LaunchedEffect(key1 = categories) {
                if (selectedCategory != null) {
                    pagerState.scrollToPage(
                        categories.indexOf(selectedCategory)
                    )
                }

                snapshotFlow { pagerState.currentPage }.collect { index ->
                    if (categories.isNotEmpty()) {
                        viewModel.onEvent(NewMonthlyTransactionEvent.UpdateCategory(categories[index]))
                    }
                }
            }

            NewTransactionContentSecond(
                modifier = Modifier
                    .constrainAs(content) {
                        start.linkTo(parent.start)
                        top.linkTo(topBar.bottom, margin = 16.dp)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    },
                selectedCategory = selectedCategory,
                categories = categories,
                concept = concept,
                description = description,
                date = "",
                isDateModificationEnabled = false,
                isAdditionalInfoValid = isAdditionalInfoValid,
                pagerState = pagerState,
                onFinishClick = {
                    viewModel.onEvent(
                        NewMonthlyTransactionEvent.SubmitTransaction(
                            MonthlyTransactionPLO(
                                id = -1,
                                concept = concept,
                                category = selectedCategory!!,
                                isActive = true,
                                quantity = if (!isExpense) totalQuantity.toDouble() else ("-$totalQuantity").toDouble()
                            )
                        )
                    )
                    navController.popBackStack()
                },
                onConceptValueChange = {
                    viewModel.onEvent(NewMonthlyTransactionEvent.UpdateConcept(it))
                },
                onDescriptionValueChange = {
                    viewModel.onEvent(NewMonthlyTransactionEvent.UpdateDescription(it))
                }
            )
        }
    }
}