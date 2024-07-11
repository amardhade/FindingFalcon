package com.gt.findingfalcon.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.gt.findingfalcon.R
import com.gt.findingfalcon.Routes
import kotlinx.coroutines.delay

@Preview
@Composable
fun SplashScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navigateTo: (route: String) -> Unit = {},
) {
    val context = LocalContext.current;
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = context.getString(R.string.finding_falcone_title))
        }

    }

    LaunchedEffect(key1 = true) {
        delay(2000)
        navigateTo(Routes.PLANET_SCREEN)
    }


}