package com.gt.findingfalcon.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.gt.findingfalcon.MainActivityViewModel
import com.gt.findingfalcon.R
import com.gt.findingfalcon.components.AppTextField
import com.gt.findingfalcon.components.AppTextFieldWithAnnotatedText
import com.gt.findingfalcon.utilities.LocalSpacing

@Composable
fun ResultScreen(
    token: String,
    mainActivityViewModel: MainActivityViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val localSpacing = LocalSpacing.current
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (mainActivityViewModel.resultantState.showLoader)
            CircularProgressIndicator()
        else
            AppTextFieldWithAnnotatedText(
                annotatedString = getResultantText(mainActivityViewModel, context),
                fontSize = localSpacing.textLarge
            )
    }

    LaunchedEffect(key1 = true) {
        mainActivityViewModel.findPlanets(token = token)
    }
}

fun getResultantText(
    mainActivityViewModel: MainActivityViewModel,
    context: Context
): AnnotatedString {
    if (mainActivityViewModel.resultantState.planetName != null)
        return buildAnnotatedString {
            append("${context.getText(R.string.planet_found)}: ")
            withStyle(style = SpanStyle(Color.Blue)) {
                append(mainActivityViewModel.resultantState.planetName)
            }
            append("\n${context.getText(R.string.time_taken)}: ")
            withStyle(style = SpanStyle(Color.Blue)) {
                append(mainActivityViewModel.resultantState.timeTaken.toString())
            }
        }
    else return buildAnnotatedString {
        withStyle(style = SpanStyle(Color.Red)) {
            append(context.getText(R.string.planet_not_found))
        }
    }
}
