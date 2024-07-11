package com.gt.findingfalcon.model

data class Planet(
    var name: String? = null,
    var distance: Int = 0,
    var timeTaken: Int = 0,
    var selectedVehicle: Vehicle? = null
)
