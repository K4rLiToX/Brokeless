package com.carlosdiestro.brokeless.main.wallet.ui.category_limit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.carlosdiestro.brokeless.core.ui.components.BrokelessKeyboard
import com.carlosdiestro.brokeless.core.ui.components.BrokelessQuantity
import com.carlosdiestro.brokeless.core.ui.components.BrokelessTopBar
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingButtons
import com.carlosdiestro.brokeless.utils.brokelessContentStyle

@Composable
fun CategoryLimitScreen(
    navController: NavController,
    viewModel: CategoryLimitViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val currency = state.value.currency
    val category = state.value.category
    val limit = state.value.limit

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (topBar, limitSection, content) = createRefs()

        category?.let {
            BrokelessTopBar(
                modifier = Modifier
                    .constrainAs(topBar) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                leadIconId = R.drawable.ic_back_arrow,
                titleId = it.textId,
                onLeadIconClick = {
                    navController.popBackStack()
                }
            )
        }

        Column(
            modifier = Modifier
                .constrainAs(limitSection) {
                    start.linkTo(parent.start)
                    top.linkTo(topBar.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                },
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.subsection_category_overview_monthly_limit),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            currency?.let {
                BrokelessQuantity(
                    quantity = limit,
                    currency = it,
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        CategoryLimitSection(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(limitSection.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            onKeyClick = {
                viewModel.onEvent(CategoryLimitEvent.UpdateLimit(it))
            },
            onNoLimitClick = {
                viewModel.onEvent(CategoryLimitEvent.NoLimit)
            },
            onUpdateClick = {
                viewModel.onEvent(CategoryLimitEvent.SubmitLimit)
                navController.popBackStack()
            }
        )
    }
}

@Composable
fun CategoryLimitSection(
    modifier: Modifier = Modifier,
    onKeyClick: (String) -> Unit,
    onNoLimitClick: () -> Unit,
    onUpdateClick: () -> Unit
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
                    bottom.linkTo(buttons.top, margin = 24.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            onKeyClicked = onKeyClick
        )

        OnBoardingButtons(
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            enabled = true,
            leftIconId = null,
            leftTextId = R.string.action_no_limit,
            rightIconId = null,
            rightTextId = R.string.action_update,
            onBackClicked = onNoLimitClick,
            onNextClicked = onUpdateClick
        )
    }
}