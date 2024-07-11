package com.gt.findingfalcon

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gt.findingfalcon.components.AppToolbarType
import com.gt.findingfalcon.components.FindingFalconAppBar
import com.gt.findingfalcon.ui.theme.FindingFalconTheme
import com.gt.findingfalcon.utilities.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//            val mainActivityViewModel: MainActivityViewModel = hilt

        setContent {
            FindingFalconTheme {
                // A surface container using the 'background' color from the theme
                MainActivityWrapper(finishActivity = { this.finish() })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityWrapper(finishActivity: () -> Unit) {

    val navController: NavHostController = rememberNavController()
    val mainActivityViewModel: MainActivityViewModel = hiltViewModel() as MainActivityViewModel
    val scaffoldState:ScaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val context = LocalContext.current

    when (navBackStackEntry?.destination?.route) {
        Routes.SPLASH_SCREEN -> {
            mainActivityViewModel.updateTopAppBarVisibility(shouldVisible = false);
        }

        Routes.PLANET_SCREEN, Routes.RESULT_SCREEN + "/{token}" -> {
            mainActivityViewModel.updateTopAppBarVisibility(shouldVisible = true);
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                FindingFalconAppBar(
                    onActionPerformed = {
                        when (mainActivityViewModel.toolBarType()) {
                            AppToolbarType.BACK -> {
                                when (navBackStackEntry?.destination?.route) {
                                    Routes.SPLASH_SCREEN -> {

                                    }

                                    Routes.PLANET_SCREEN, Routes.RESULT_SCREEN+"/{token}" -> {
                                        mainActivityViewModel.postUIEvent(UiEvent.NavigateUp)
                                    }
                                }
                            }

                            AppToolbarType.HAMBURGER -> {}
                            else -> {}
                        }
                    }
                )
            },
            content = { paddingValues ->
                    NavigationManager(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                        navHostController = navController,
                        mainActivityViewModel = mainActivityViewModel
                    )
            }
        )
    }


    LaunchedEffect(key1 = true) {
        mainActivityViewModel.activityUIEvent.collect {
            when (it) {
                is UiEvent.NavigateUp -> {
                        finishActivity()
                }
                is UiEvent.ShowSnackBar -> {
                    Toast.makeText(context, it.msg.asString(context), Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

}