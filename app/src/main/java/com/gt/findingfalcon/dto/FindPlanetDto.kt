package com.gt.findingfalcon.dto

import com.google.gson.annotations.SerializedName

data class FindPlanetDto(
    var status: String,
    @SerializedName("planet_name")
    var planetName: String,
    var error: String
)