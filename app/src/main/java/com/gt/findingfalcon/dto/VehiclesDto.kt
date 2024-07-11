package com.gt.findingfalcon.dto

import com.google.gson.annotations.SerializedName

class VehiclesDto {
    var name: String? = null

    @SerializedName("total_no")
    var totalNumber: Int = 0

    @SerializedName("max_distance")
    var maxDistance: Double = 0.0

    var speed: Int = 0
}