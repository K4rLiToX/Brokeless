package com.carlosdiestro.brokeless.main.new_transaction.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
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
import com.carlosdiestro.brokeless.main.budget.ui.models.TransactionPLO
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.NewTransactionContentFirst
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.NewTransactionContentSecond
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.NewTransactionEvent
import com.carlosdiestro.brokeless.onboarding.ui.new_transaction.NewTransactionViewModel
import com.carlosdiestro.brokeless.utils.TimeManager
import com.google.accompanist.pager.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewTransactionScreen(
    navController: NavController,
    viewModel: NewTransactionViewModel = hiltViewModel(),
    isPayment: Boolean = true
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val totalQuantity = state.value.quantity
    val isTotalQuantityValid = state.value.isTotalQuantityValid
    val isAdditionalInfoValid = state.value.isAdditionalInfoValid
    val categories = state.value.categories
    val selectedCategory = state.value.selectedCategory
    val concept = state.value.concept
    val description = state.value.description
    val date = state.value.date
    val isDateModificationEnabled = state.value.isDateModificationEnabled
    val currentPage = state.value.currentPage

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
            titleId = if (isPayment) R.string.section_new_payment else R.string.section_new_addition,
            leadIconId = R.drawable.ic_back_arrow,
            onLeadIconClick = {
                if (currentPage == 1) navController.popBackStack()
                else viewModel.onEvent(NewTransactionEvent.ChangePage)
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
                isContinueEnabled = isTotalQuantityValid,
                onContinueClick = {
                    viewModel.onEvent(NewTransactionEvent.ChangePage)
                },
                onKeyClicked = {
                    viewModel.onEvent(NewTransactionEvent.UpdateQuantity(it))
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
                        viewModel.onEvent(NewTransactionEvent.UpdateCategory(categories[index]))
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
                date = date,
                isDateModificationEnabled = isDateModificationEnabled,
                isAdditionalInfoValid = isAdditionalInfoValid,
                pagerState = pagerState,
                onFinishClick = {
                    viewModel.onEvent(
                        NewTransactionEvent.SubmitTransaction(
                            TransactionPLO(
                                id = -1,
                                concept = concept,
                                description = description,
                                quantity = if (isPayment) "-$totalQuantity".toDouble() else totalQuantity.toDouble(),
                                category = selectedCategory!!,
                                date = TimeManager.toStringDate(System.currentTimeMillis())
                            )
                        )
                    )
                    navController.popBackStack()
                },
                onConceptValueChange = {
                    viewModel.onEvent(NewTransactionEvent.UpdateConcept(it))
                },
                onDescriptionValueChange = {
                    viewModel.onEvent(NewTransactionEvent.UpdateDescription(it))
                }
            )
        }
    }
}