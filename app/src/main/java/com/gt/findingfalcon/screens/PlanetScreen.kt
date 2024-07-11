package com.gt.findingfalcon.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.gt.findingfalcon.MainActivityViewModel
import com.gt.findingfalcon.R
import com.gt.findingfalcon.components.AppButton
import com.gt.findingfalcon.components.AppDropDownMenu
import com.gt.findingfalcon.model.DropDownItem
import com.gt.findingfalcon.model.Planet
import com.gt.findingfalcon.model.Vehicle
import com.gt.findingfalcon.utilities.LocalSpacing
import com.gt.findingfalcon.utilities.UiEvent

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun PlanetScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navigateTo: (route: String) -> Unit = {},
    mainActivityViewModel: MainActivityViewModel = hiltViewModel() as MainActivityViewModel
) {

    val context = LocalContext.current
    val localSpacing = LocalSpacing.current
    val planets: MutableList<DropDownItem<String>> = mutableListOf()
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()



    fun prepareDropDownList(list: MutableList<Planet>): MutableList<DropDownItem<Any>> {
        val items = mutableListOf<DropDownItem<Any>>()
        list.forEachIndexed { index, planet ->
            items.add(
                DropDownItem(
                    id = index,
                    title = planet.name ?: "",
                    subTile = planet.distance.toDouble(),
                    details = planet
                )
            )
        }
        return items
    }

    fun prepareDropDownList(list: MutableList<Vehicle>): MutableList<DropDownItem<Any>> {
        val items = mutableListOf<DropDownItem<Any>>()
        list.forEachIndexed { index, planet ->
            items.add(
                DropDownItem(
                    id = index,
                    title = planet.name,
                    subTile = planet.maxDistance,
                    details = planet
                )
            )
        }
        return items
    }

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        keyboardController?.hide()
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,

            ) {
            val selectedPlanets = mainActivityViewModel.selectedPlanets
            selectedPlanets.forEachIndexed { index, planet ->
                AppDropDownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(localSpacing.spaceMedium),
                    dropDownList = prepareDropDownList(mainActivityViewModel.planetState.planets),
                    placeholderText = stringResource(id = R.string.select_planet),
                    labelText = stringResource(id = R.string.select_planet) + " " + (index + 1),
                    toggleDropdown = { selectedItem, _ ->
                        mainActivityViewModel.updateSelectedPlanets(selectedItem, index)
                    },
                    loading = mainActivityViewModel.planetState.showLoader
                )

                if (mainActivityViewModel.selectedPlanets[index].name != null) {
                    AppDropDownMenu(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = localSpacing.spaceLarge
                            )
                            .focusable(false)
                            .padding(localSpacing.spaceMedium),
                        dropDownList = prepareDropDownList(mainActivityViewModel.planetState.availableVehicles),
                        placeholderText = stringResource(id = R.string.select_vehicles),
                        labelText = stringResource(id = R.string.select_vehicles),
                        toggleDropdown = { selectedItem, _ ->
                            mainActivityViewModel.updateSelectedVehicle(
                                selectedVehicle = selectedItem,
                                index
                            )
                        }
                    )
                }
            }

            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = localSpacing.spaceSmall),
                textLabel = "Submit",
                isEnabled = mainActivityViewModel.shouldEnableSubmitAction(mainActivityViewModel.selectedPlanets)
            ) {
                mainActivityViewModel.getToken()
            }
        }

    }

    LaunchedEffect(key1 = true) {
        mainActivityViewModel.fetchPlanets()
        mainActivityViewModel.fetchVehicles()
        mainActivityViewModel.planetScreenUIEvent.collect { event ->
            when (event) {
                is UiEvent.NavigateTo -> {
                    navigateTo(event.routeToNav!!)
                }

                is UiEvent.NavigateUp -> {}
                is UiEvent.ShowSnackBar -> {
                    Toast.makeText(
                        context,
                        event.msg.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {}
            }
        }
    }

}