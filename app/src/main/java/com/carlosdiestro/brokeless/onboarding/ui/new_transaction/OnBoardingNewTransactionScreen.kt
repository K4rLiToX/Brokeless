package com.carlosdiestro.brokeless.onboarding.ui.new_transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessButton
import com.carlosdiestro.brokeless.core.ui.components.cards.SimpleCategoryCard
import com.carlosdiestro.brokeless.core.ui.models.CategoryPLO
import com.carlosdiestro.brokeless.core.ui.theme.JetBrainsMono
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat
import com.carlosdiestro.brokeless.main.wallet.ui.models.MonthlyTransactionPLO
import com.carlosdiestro.brokeless.onboarding.OnBoardingEvent
import com.carlosdiestro.brokeless.onboarding.OnBoardingViewModel
import com.carlosdiestro.brokeless.utils.brokelessContentStyle
import com.carlosdiestro.brokeless.utils.pagerAnimation
import com.google.accompanist.pager.*
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingNewTransactionScreen(
    navController: NavController,
    onBoardingViewModel: OnBoardingViewModel,
    isExpense: Boolean = false,
    viewModel: NewTransactionViewModel = hiltViewModel()
) {
    val onBoardingState = onBoardingViewModel.state.collectAsState()
    val state = viewModel.state.collectAsState()

    val currency = onBoardingState.value.selectedCurrency
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
            titleId = if (isExpense) R.string.section_new_expense else R.string.section_new_income,
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
                    onBoardingViewModel.onEvent(
                        OnBoardingEvent.UpdateFixedTransactions(
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
                    viewModel.onEvent(NewTransactionEvent.UpdateConcept(it))
                },
                onDescriptionValueChange = {
                    viewModel.onEvent(NewTransactionEvent.UpdateDescription(it))
                }
            )
        }
    }
}

@Composable
fun NewTransactionContentFirst(
    modifier: Modifier = Modifier,
    isContinueEnabled: Boolean,
    onContinueClick: () -> Unit,
    onKeyClicked: (String) -> Unit
) {

    ConstraintLayout(
        modifier = modifier.brokelessContentStyle()
    ) {
        val (keyboard, button) = createRefs()

        BrokelessKeyboard(
            modifier = Modifier
                .constrainAs(keyboard) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top, margin = 42.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            onKeyClicked = onKeyClicked
        )

        BrokelessButton(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            textId = R.string.action_continue,
            rightIconId = R.drawable.ic_chevron_right,
            onClick = onContinueClick,
            enabled = isContinueEnabled
        )
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewTransactionContentSecond(
    modifier: Modifier = Modifier,
    selectedCategory: CategoryPLO?,
    categories: List<CategoryPLO>,
    concept: String,
    description: String,
    date: String,
    isDateModificationEnabled: Boolean,
    isAdditionalInfoValid: Boolean,
    pagerState: PagerState,
    onConceptValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onFinishClick: () -> Unit
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
        val (categorySectionTitle, categoryPager, categoryText, conceptTextField, descriptionTextField, dateSection, button) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(categorySectionTitle) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                },
            text = stringResource(id = R.string.subsection_new_movement_choose_category),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )

        CategoryPager(
            modifier = Modifier
                .constrainAs(categoryPager) {
                    start.linkTo(parent.start)
                    top.linkTo(categorySectionTitle.bottom, margin = 32.dp)
                    end.linkTo(parent.end)
                },
            pagerState = pagerState,
            categories = categories
        )

        Text(
            modifier = Modifier
                .constrainAs(categoryText) {
                    start.linkTo(parent.start)
                    top.linkTo(categoryPager.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                },
            text = selectedCategory?.let { stringResource(id = it.textId) } ?: "",
            style = TextStyle(
                fontSize = 22.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.SemiBold
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(conceptTextField) {
                    start.linkTo(parent.start)
                    top.linkTo(categoryText.bottom, margin = 48.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            label = {
                Text(
                    text = stringResource(id = R.string.label_concept),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.04.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
            value = concept,
            onValueChange = onConceptValueChange,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(descriptionTextField) {
                    start.linkTo(parent.start)
                    top.linkTo(conceptTextField.bottom, margin = 24.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            label = {
                Text(
                    text = stringResource(id = R.string.label_description),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.04.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
            value = description,
            onValueChange = onDescriptionValueChange,
            maxLines = 3,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontFamily = Montserrat,
                fontWeight = FontWeight.Medium
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        BrokelessButton(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            textId = R.string.action_finish,
            leftIconId = R.drawable.ic_check,
            enabled = isAdditionalInfoValid,
            onClick = onFinishClick
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    categories: List<CategoryPLO>
) {
    HorizontalPager(
        modifier = modifier,
        count = categories.size,
        state = pagerState,
        contentPadding = PaddingValues(start = 120.dp, end = 120.dp)
    ) { index ->
        val category = categories[index]
        val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue
        SimpleCategoryCard(
            modifier = Modifier.pagerAnimation(pageOffset),
            iconId = category.iconId
        )
    }
}