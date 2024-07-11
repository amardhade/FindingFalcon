package com.gt.findingfalcon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gt.findingfalcon.components.AppToolbarType
import com.gt.findingfalcon.components.TopAppBar
import com.gt.findingfalcon.model.DropDownItem
import com.gt.findingfalcon.model.Planet
import com.gt.findingfalcon.model.Vehicle
import com.gt.findingfalcon.repo.PlanetRepoListener
import com.gt.findingfalcon.screens.PlanetState
import com.gt.findingfalcon.screens.ResultantState
import com.gt.findingfalcon.utilities.UiEvent
import com.gt.findingfalcon.utilities.UiText
import com.xpressbees.core.wrappers.ApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val planetRepoListener: PlanetRepoListener) :
    ViewModel() {

    private val _activityUIEvent = Channel<UiEvent>()
    val activityUIEvent = _activityUIEvent.receiveAsFlow()

    private val _planetScreenUIEvent = Channel<UiEvent>()
    val planetScreenUIEvent = _planetScreenUIEvent.receiveAsFlow()

    var selectedPlanets = mutableListOf(Planet(), Planet(), Planet(), Planet())

    var planetState by mutableStateOf(
        PlanetState(
            planets = mutableListOf(),
            showLoader = false,
            vehicles = mutableListOf(),
            availableVehicles = mutableListOf()
        )
    )

    var resultantState by mutableStateOf(
        ResultantState(
            planetName = "",
            timeTaken = null,
            showLoader = false
        )
    )


    private var topAppBarState by mutableStateOf(
        TopAppBar(
            shouldDisplayAppBar = false,
            toolbarType = AppToolbarType.BACK
        )
    )

    fun updateTopAppBarVisibility(shouldVisible: Boolean) {
        topAppBarState = topAppBarState.copy(
            shouldDisplayAppBar = shouldVisible
        )
    }

    fun shouldDisplayTopBar() = topAppBarState.shouldDisplayAppBar

    fun toolBarType(): AppToolbarType = topAppBarState.toolbarType

    fun postUIEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.NavigateTo -> {

            }

            is UiEvent.NavigateUp -> {
                viewModelScope.launch {
                    _activityUIEvent.send(UiEvent.NavigateUp)
                }
            }

            is UiEvent.ShowSnackBar -> {

            }

            else -> {}
        }

    }

    private fun showLoading(shouldVisible: Boolean) {
        planetState = planetState.copy(
            showLoader = shouldVisible
        )
    }

    fun fetchPlanets() {
        showLoading(true)
        viewModelScope.launch {
            val planetResponse = planetRepoListener.fetchPlanets()
            showLoading(false)
            when (planetResponse.apiStatus) {
                ApiStatus.Loading -> {}
                ApiStatus.Completed -> {
                    planetState = planetState.copy(
                        planets = planetResponse.response!!
                    )
                }

                ApiStatus.Exception -> {
                    _planetScreenUIEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.somehing_went_wrong)))
                }

                else -> {}
            }
        }
    }

    fun fetchVehicles() {
        showLoading(true)
        viewModelScope.launch {
            val planetResponse = planetRepoListener.fetchVehicles()
            showLoading(false)
            when (planetResponse.apiStatus) {
                ApiStatus.Loading -> {}
                ApiStatus.Completed -> {
                    planetState = planetState.copy(
                        vehicles = planetResponse.response!!
                    )
                }

                ApiStatus.Exception -> {
                    _planetScreenUIEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.somehing_went_wrong)))
                }

                else -> {}
            }
        }
    }

    fun updateSelectedPlanets(selectedPlanet: DropDownItem<Any>?, planetToUpdate: Int) {
        val planet = selectedPlanets[planetToUpdate]
        planet.name = selectedPlanet?.title ?: ""
        planet.distance = selectedPlanet?.subTile!!.toInt()
        prepareAvailableVehicles(selectedPlanet)
    }

    fun updateSelectedVehicle(selectedVehicle: DropDownItem<Any>?, vehicleToUpdate: Int) {
        val planet = selectedPlanets[vehicleToUpdate]
        planet.selectedVehicle = selectedVehicle?.details as Vehicle
        planet.timeTaken = timeTaken(
            planet.distance,
            (selectedVehicle.details as Vehicle).speed
        )
    }

    private fun timeTaken(distance: Int, speed: Int): Int = distance / speed


    private fun prepareAvailableVehicles(selectedPlanet: DropDownItem<Any>?) {
        var availableVehicles: List<Vehicle> = mutableListOf()
        if (planetState.vehicles.isNotEmpty()) {
            availableVehicles = planetState.vehicles.filter { vehicle ->
                vehicle.maxDistance >= (selectedPlanet?.subTile ?: 0.0)
            }
        }
        planetState = planetState.copy(
            availableVehicles = availableVehicles.toMutableList()
        )
    }

    fun getToken() {
        showLoading(true)
        viewModelScope.launch {
            val tokenResponse = planetRepoListener.fetchToken()
            showLoading(false)
            when (tokenResponse.apiStatus) {
                ApiStatus.Loading -> {}
                ApiStatus.Completed -> {
                    _planetScreenUIEvent.send(UiEvent.NavigateTo(Routes.RESULT_SCREEN + "/${tokenResponse.response!!}"))
                }

                ApiStatus.Exception -> {
                    _planetScreenUIEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.no_network)))
                }

                else -> {}
            }
        }
    }

    private fun timeTaken(planetName: String): Int? {
        for (planet in selectedPlanets) {
            planet.name.equals(planetName, true)
            return planet.distance.div(planet.selectedVehicle?.speed!!)
        }
        return null
    }

    fun shouldEnableSubmitAction(planets: MutableList<Planet>): Boolean {
        if (planetState.showLoader) return false
        for (planet in planets) {
            if (planet.name == null) return false
            continue
        }
        return true
    }

    fun findPlanets(token: String) {
        showLoaderOnResultantScreen(true)
        viewModelScope.launch {
            val findPlanetReqModel = FindPlanetReqModel(
                token = token,
                selectedPlanets = getSelPlanets(),
                selectedVehicles = getSelectedVehicles()
            )
            val findPlanetResponse = planetRepoListener.findPlanet(findPlanetReqModel)
            showLoaderOnResultantScreen(false)
            when (findPlanetResponse.apiStatus) {
                ApiStatus.Loading -> {}
                ApiStatus.Completed -> {
                    resultantState = if (findPlanetResponse.response?.planetName != null) {
                        val planetName = findPlanetResponse.response?.planetName!!
                        timeTaken(planetName)
                        resultantState.copy(
                            planetName = planetName,
                            timeTaken = timeTaken(planetName)
                        )
                    } else resultantState.copy(
                        planetName = null
                    )
                }

                ApiStatus.Exception -> {
                    _activityUIEvent.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.somehing_went_wrong)))
                }

                else -> {}
            }
        }
    }

    private fun showLoaderOnResultantScreen(showLoader: Boolean) {
        resultantState = resultantState.copy(
            showLoader = showLoader
        )
    }

    private fun getSelPlanets(): List<String> {
        val planets = mutableListOf<String>()
        for (planet in selectedPlanets) {
            planets.add(planet.name!!)
        }
        return planets
    }

    private fun getSelectedVehicles(): List<String> {
        val vehicles = mutableListOf<String>()
        for (planet in selectedPlanets) {
            vehicles.add(planet.selectedVehicle?.name!!)
        }
        return vehicles
    }

}