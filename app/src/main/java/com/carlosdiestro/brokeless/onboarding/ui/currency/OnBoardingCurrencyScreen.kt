package com.carlosdiestro.brokeless.onboarding.ui.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessButton
import com.carlosdiestro.brokeless.core.ui.components.cards.CurrencyCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.onboarding.OnBoardingEvent
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingHeader
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingCurrencyScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel,
    viewModel: OnBoardingCurrencyViewModel = hiltViewModel()
) {

    val onBoardingState = onBoardingViewModel.state.collectAsState()
    val state = viewModel.state.collectAsState()

    val selectedCurrency = onBoardingState.value.selectedCurrency
    val currencies = state.value.currencies

    val pagerState = rememberPagerState()

    LaunchedEffect(key1 = currencies) {
        if (selectedCurrency != null) {
            pagerState.scrollToPage(
                currencies.indexOf(
                    selectedCurrency
                )
            )
        }
        snapshotFlow { pagerState.currentPage }.collect { index ->
            if (currencies.isNotEmpty()) {
                onBoardingViewModel.onEvent(OnBoardingEvent.UpdateSelectedCurrency(currencies[index]))
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (header, content) = createRefs()

        OnBoardingHeader(
            modifier = Modifier
                .constrainAs(header) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 40.dp)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 24.dp),
            textId = R.string.header_on_boarding_currency
        )

        CurrencyContent(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(header.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            currencies = currencies,
            pagerState = pagerState,
            onNextClick = {
                navController.navigate(NavigationDirections.OnBoarding.balance.destination)
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CurrencyContent(
    modifier: Modifier = Modifier,
    currencies: List<CurrencyPLO>,
    pagerState: PagerState,
    onNextClick: () -> Unit
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
        val (currencyPager, button) = createRefs()

        CurrencyPager(
            modifier = Modifier
                .constrainAs(currencyPager) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top, margin = 54.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            currencies = currencies,
            state = pagerState
        )

        BrokelessButton(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            textId = R.string.action_next,
            rightIconId = R.drawable.ic_chevron_right
        ) {
            onNextClick()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CurrencyPager(
    modifier: Modifier = Modifier,
    currencies: List<CurrencyPLO>,
    state: PagerState
) {
    VerticalPager(
        modifier = modifier,
        count = currencies.size,
        state = state,
        contentPadding = PaddingValues(top = 150.dp, bottom = 150.dp)
    ) { index ->
        val currency = currencies[index]
        CurrencyCard(
            modifier = modifier.graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue

                lerp(
                    start = 0.75F,
                    stop = 1F,
                    fraction = 1F - pageOffset.coerceIn(0F, 1F)
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }

                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                )
            },
            currency = currency
        )
    }
}