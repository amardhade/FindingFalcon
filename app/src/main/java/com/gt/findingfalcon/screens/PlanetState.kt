package com.gt.findingfalcon.screens

import com.gt.findingfalcon.dto.VehiclesDto
import com.gt.findingfalcon.model.Planet
import com.gt.findingfalcon.model.Vehicle

data class PlanetState(
    var planets: MutableList<Planet>,
    var vehicles: MutableList<Vehicle>,
    var showLoader: Boolean,
    var availableVehicles: MutableList<Vehicle>,
//    var selectedPlanets: MutableList<Planet>,
//    var selectedPlanet1: Planet? = null,
//    var selectedPlanet2: Planet? = null,
//    var selectedPlanet3: Planet? = null,
//    var selectedPlanet4: Planet? = null,
//    var selectedVehicle1: Vehicle? = null,
//    var selectedVehicle2: Vehicle? = null,
//    var selectedVehicle3: Vehicle? = null,
//    var selectedVehicle4: Vehicle? = null,

    var timeTaken: Int = 0

)

data class ResultantState(
    var planetName: String?,
    var timeTaken: Int?,
    var showLoader: Boolean
)
