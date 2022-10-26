package com.carlosdiestro.brokeless.welcome.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.carlosdiestro.brokeless.R
import com.carlosdiestro.brokeless.core.navigation.NavigationDirections
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessButton
import com.carlosdiestro.brokeless.core.ui.components.buttons.BrokelessOutlinedButton
import com.carlosdiestro.brokeless.core.ui.theme.Montserrat

@Composable
fun WelcomeScreen(
    navController: NavController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val isFirstTime = state.value.isFirstTime

    Log.d("DEBUG", "Is First Time: $isFirstTime")

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        val (logoSection, blankLoader, getStartedSection, logInSection) = createRefs()

        if (isFirstTime == null) {
            BlankLoader(
                modifier = Modifier
                    .constrainAs(blankLoader) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
        } else {
            LogoSection(
                modifier = Modifier
                    .constrainAs(logoSection) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, margin = 88.dp)
                        end.linkTo(parent.end)
                    },
                showAppName = !isFirstTime
            )
            if (isFirstTime) {
                GetStartedSection(
                    modifier = Modifier
                        .constrainAs(getStartedSection) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    navController.navigate(NavigationDirections.OnBoarding.root.destination)
                }
            } else {
                LogInSection(
                    modifier = Modifier
                        .constrainAs(logInSection) {
                            start.linkTo(parent.start)
                            top.linkTo(logoSection.bottom, margin = 56.dp)
                            end.linkTo(parent.end)
                        },
                    fingerPrintAction = {
                        navController.navigate(NavigationDirections.Main.root.destination) {
                            popUpTo(NavigationDirections.welcome.destination) {
                                inclusive = true
                            }
                        }
                        /*TODO(Popup fingerprint dialog)*/
                    },
                    pinAction = { /*TODO(Popup pin or pattern dialog)*/ }
                )
            }
        }
    }
}

@Composable
fun LogoSection(
    modifier: Modifier = Modifier,
    showAppName: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(240.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "App Logo"
        )
        if (showAppName) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun GetStartedSection(
    modifier: Modifier = Modifier,
    navAction: () -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (slogan, button) = createRefs()

        Column(
            modifier = Modifier
                .constrainAs(slogan) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top, margin = 40.dp)
                },
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.25.sp
                )
            )
            Text(
                text = stringResource(id = R.string.app_slogan),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 3
            )
        }

        BrokelessButton(
            modifier = Modifier
                .constrainAs(button) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth(),
            textId = R.string.action_get_started
        ) {
            navAction()
        }
    }
}

@Composable
fun LogInSection(
    modifier: Modifier = Modifier,
    fingerPrintAction: () -> Unit,
    pinAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BrokelessButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.action_access_fingerprint
        ) {
            fingerPrintAction()
        }
        BrokelessOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.action_access_pin_or_pattern
        ) {
            pinAction()
        }
    }
}

@Composable
fun BlankLoader(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier)
}