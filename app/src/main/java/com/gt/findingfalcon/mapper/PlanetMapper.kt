package com.gt.findingfalcon.mapper

import com.gt.findingfalcon.dto.FindPlanetDto
import com.gt.findingfalcon.dto.PlanetDto
import com.gt.findingfalcon.model.FindPlanet
import com.gt.findingfalcon.model.Planet


fun toPlanet(planetDto: PlanetDto): Planet {
    return Planet(
        name = planetDto.name ?: "",
        distance = planetDto.distance
    )
}

fun toFindPlanet(findPlanetDto: FindPlanetDto): FindPlanet {
    return FindPlanet(
        status = findPlanetDto.status,
        planetName = findPlanetDto.planetName,
        error = findPlanetDto.error
    )
}