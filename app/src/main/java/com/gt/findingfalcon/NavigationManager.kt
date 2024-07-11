package com.gt.findingfalcon

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gt.findingfalcon.screens.PlanetScreen
import com.gt.findingfalcon.screens.ResultScreen
import com.gt.findingfalcon.screens.SplashScreen
import com.gt.findingfalcon.utilities.isValidString

@Composable
fun NavigationManager(
    modifier: Modifier,
    launchScreen: String = Routes.SPLASH_SCREEN,
    navHostController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    mainActivityViewModel: MainActivityViewModel
) {

    NavHost(navController = navHostController, startDestination = launchScreen) {

        composable(route = Routes.SPLASH_SCREEN) {
            SplashScreen(scaffoldState = scaffoldState, navigateTo = { route ->
                navHostController.navigate(route) {
                    popUpTo(Routes.SPLASH_SCREEN) {
                        inclusive = true
                    }
                }
            })
        }

        composable(route = Routes.PLANET_SCREEN) {
            PlanetScreen(scaffoldState = scaffoldState, navigateTo = { route ->
                navHostController.navigate(route)
            }, mainActivityViewModel)
        }

        composable(route = Routes.RESULT_SCREEN + "/{token}",
            arguments = listOf(
                navArgument("token") {
                    type = NavType.StringType
                    nullable = true
                }
            )) {
            val token = it.arguments?.getString("token")
            if (token.isValidString())
                ResultScreen(token!!, mainActivityViewModel)
        }
    }

}