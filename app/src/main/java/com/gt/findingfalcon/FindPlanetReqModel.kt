package com.gt.findingfalcon

import com.google.gson.annotations.SerializedName

data class FindPlanetReqModel(
    var token: String,
    @SerializedName("planet_names")
    var selectedPlanets: List<String>,
    @SerializedName("vehicle_names")
    var selectedVehicles: List<String>
)
