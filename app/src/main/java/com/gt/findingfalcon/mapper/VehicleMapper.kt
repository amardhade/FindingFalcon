package com.gt.findingfalcon.mapper

import com.gt.findingfalcon.dto.VehiclesDto
import com.gt.findingfalcon.model.Vehicle


fun VehiclesDto.toVehicle(vehiclesDto: VehiclesDto): Vehicle {
    return Vehicle(
        name = vehiclesDto.name ?: "",
        totalVehicle = vehiclesDto.totalNumber,
        maxDistance = vehiclesDto.maxDistance,
        speed = vehiclesDto.speed

    )
}