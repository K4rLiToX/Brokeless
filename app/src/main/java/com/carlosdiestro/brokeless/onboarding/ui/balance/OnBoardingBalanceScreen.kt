package com.carlosdiestro.brokeless.onboarding.ui.balance

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.components.BrokelessKeyboard
import com.carlosdiestro.brokeless.core.ui.components.BrokelessQuantity
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.onboarding.OnBoardingEvent
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingButtons
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingHeader
import com.carlosdiestro.brokeless.utils.brokelessContentStyle

@Composable
fun OnBoardingBalanceScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel,
    viewModel: OnBoardingBalanceViewModel = hiltViewModel()
) {
    val onBoardingState = onBoardingViewModel.state.collectAsState()
    val state = viewModel.state.collectAsState()

    val totalBalanceText = onBoardingState.value.totalBalance
    val currency = onBoardingState.value.selectedCurrency
    val isBalanceCorrect = state.value.isBalanceCorrect

    LaunchedEffect(key1 = totalBalanceText) {
        viewModel.onEvent(OnBoardingBalanceEvent.ValidateBalance(totalBalanceText))
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, balance, content) = createRefs()

        OnBoardingHeader(
            modifier = Modifier
                .constrainAs(header) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 40.dp)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 24.dp),
            textId = R.string.header_on_boarding_balance,
        )

        BrokelessQuantity(
            modifier = Modifier
                .constrainAs(balance) {
                    start.linkTo(parent.start)
                    top.linkTo(header.bottom, margin = 52.dp)
                    end.linkTo(parent.end)
                },
            quantity = totalBalanceText,
            currency = currency!!,
            style = TextStyle(
                fontSize = 40.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            )
        )

        BalanceContent(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(balance.bottom, margin = 36.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            isBalanceCorrect = isBalanceCorrect,
            onKeyClicked = { value ->
                onBoardingViewModel.onEvent(OnBoardingEvent.UpdateTotalBalance(value))
            },
            onNextClicked = {
                navController.navigate(
                    "${NavigationDirections.OnBoarding.monthlyTransactions.destination}/true"
                )
            },
            onBackClicked = {
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun BalanceContent(
    modifier: Modifier = Modifier,
    isBalanceCorrect: Boolean,
    onKeyClicked: (String) -> Unit,
    onNextClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier.brokelessContentStyle()
    ) {
        val (keyboard, buttons) = createRefs()

        BrokelessKeyboard(
            modifier = Modifier
                .constrainAs(keyboard) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttons.top, margin = 42.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            onKeyClicked = onKeyClicked
        )

        OnBoardingButtons(
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            enabled = isBalanceCorrect,
            onNextClicked = onNextClicked,
            onBackClicked = onBackClicked
        )
    }
}