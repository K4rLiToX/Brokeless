package com.carlosdiestro.brokeless.onboarding.ui.savings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.components.cards.MonthlyTransactionCard
import com.carlosdiestro.brokeless.core.ui.models.CurrencyPLO
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.onboarding.OnBoardingEvent
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingButtons
import com.carlosdiestro.brokeless.onboarding.components.OnBoardingHeader
import com.carlosdiestro.brokeless.utils.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OnBoardingSavingsScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel
) {
    val onBoardingState = onBoardingViewModel.state.collectAsState()
    val currency = onBoardingState.value.selectedCurrency
    val incomes = onBoardingState.value.monthlyTransactions.incomes().total()
    val expenses = onBoardingState.value.monthlyTransactions.expenses().total()
    val savingsText = onBoardingState.value.savingsText
    val savingsPercentage = onBoardingState.value.savingsPercentage
    val keyboardManager = LocalSoftwareKeyboardController.current

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (header, content) = createRefs()

        OnBoardingHeader(
            modifier = Modifier
                .constrainAs(header) {
                    start.linkTo(parent.start, margin = 24.dp)
                    top.linkTo(parent.top, margin = 40.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    width = Dimension.fillToConstraints
                },
            textId = R.string.header_on_boarding_savings
        )

        SavingsContent(
            modifier = Modifier
                .constrainAs(content) {
                    start.linkTo(parent.start)
                    top.linkTo(header.bottom, margin = 40.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            currency = currency!!,
            totalIncomes = incomes,
            totalExpenses = expenses,
            savingsText = savingsText,
            savingsPercentage = savingsPercentage,
            keyboardManager = keyboardManager,
            onSavingsFieldTextChange = {
                onBoardingViewModel.onEvent(OnBoardingEvent.UpdateSavingsText(it))
            },
            onBackClick = { navController.popBackStack() },
            onFinishClick = {
                onBoardingViewModel.onEvent(OnBoardingEvent.FinishOnBoarding)
//                navController.navigate(NavigationDirections.Main.root.destination)
            },
            onSliderValueChange = {
                onBoardingViewModel.onEvent(OnBoardingEvent.UpdateSavingsPercentage(it.toDouble()))
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SavingsContent(
    modifier: Modifier = Modifier,
    currency: CurrencyPLO,
    totalIncomes: Double,
    totalExpenses: Double,
    savingsText: String,
    savingsPercentage: Double,
    keyboardManager: SoftwareKeyboardController?,
    onSavingsFieldTextChange: (String) -> Unit,
    onBackClick: () -> Unit,
    onFinishClick: () -> Unit,
    onSliderValueChange: (Float) -> Unit
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
        val (question, savingsTextField, savingsPercentageSection, overviewSection, buttons) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(question) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                },
            text = stringResource(id = R.string.subsection_on_boarding_savings_question),
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(savingsTextField) {
                    start.linkTo(parent.start)
                    top.linkTo(question.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            value = savingsText.ifBlank { "0.0" },
            trailingIcon = {
                Text(
                    text = currency.symbol,
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Normal
                    )
                )
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Medium
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                disabledBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardManager?.hide()
                }
            ),
            onValueChange = onSavingsFieldTextChange
        )

        SavingsSlider(
            modifier = Modifier
                .constrainAs(savingsPercentageSection) {
                    start.linkTo(parent.start)
                    top.linkTo(savingsTextField.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            percentage = savingsPercentage,
            onSliderValueChange = onSliderValueChange
        )

        Column(
            modifier = Modifier
                .constrainAs(overviewSection) {
                    start.linkTo(parent.start)
                    top.linkTo(savingsPercentageSection.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttons.top, margin = 32.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(id = R.string.subsection_on_boarding_savings_final_overview),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                )
            )
            MonthlyTransactionCard(
                iconId = R.drawable.ic_incomes,
                concept = stringResource(id = R.string.title_incomes),
                quantity = totalIncomes,
                currency = currency
            )
            MonthlyTransactionCard(
                iconId = R.drawable.ic_expenses,
                concept = stringResource(id = R.string.title_expenses),
                quantity = totalExpenses,
                currency = currency
            )
            MonthlyTransactionCard(
                iconId = R.drawable.ic_savings,
                concept = stringResource(id = R.string.title_savings),
                quantity = -(savingsText.toDouble()),
                currency = currency
            )
            MonthlyTransactionCard(
                iconId = R.drawable.ic_credit,
                concept = stringResource(id = R.string.title_monthly_budget),
                quantity = totalIncomes - savingsText.toDouble() + totalExpenses,
                currency = currency
            )
        }

        OnBoardingButtons(
            modifier = Modifier
                .constrainAs(buttons) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            enabled = true,
            onBackClicked = onBackClick,
            onNextClicked = onFinishClick
        )
    }
}


@Composable
fun SavingsSlider(
    modifier: Modifier = Modifier,
    percentage: Double,
    onSliderValueChange: (Float) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Slider(
            modifier = Modifier.fillMaxWidth(0.75F),
            value = percentage.toFloat(),
            valueRange = (0F..100F),
            steps = 99,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.background,
                activeTickColor = MaterialTheme.colorScheme.primary,
                inactiveTickColor = MaterialTheme.colorScheme.background
            ),
            onValueChange = onSliderValueChange
        )

        Text(
            text = stringResource(
                id = R.string.placeholder_percentage,
                percentage.round(2).toString()
            ),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
