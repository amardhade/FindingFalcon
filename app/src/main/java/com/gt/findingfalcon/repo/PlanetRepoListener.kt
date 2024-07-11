package com.gt.findingfalcon.repo

import com.gt.findingfalcon.FindPlanetReqModel
import com.gt.findingfalcon.dto.AppException
import com.gt.findingfalcon.dto.ResponseWrapper
import com.gt.findingfalcon.model.FindPlanet
import com.gt.findingfalcon.model.Planet
import com.gt.findingfalcon.model.Vehicle
import com.xpressbees.core.wrappers.ApiStatus

interface PlanetRepoListener {

    suspend fun fetchPlanets(): ResponseWrapper<MutableList<Planet>, ApiStatus, AppException>

    suspend fun fetchVehicles(): ResponseWrapper<MutableList<Vehicle>, ApiStatus, AppException>

    suspend fun fetchToken(): ResponseWrapper<String, ApiStatus, AppException>

    suspend fun findPlanet(findPlanetReqModel: FindPlanetReqModel): ResponseWrapper<FindPlanet, ApiStatus, AppException>

}