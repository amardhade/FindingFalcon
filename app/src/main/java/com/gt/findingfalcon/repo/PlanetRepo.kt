package com.gt.findingfalcon.repo

import com.gt.findingfalcon.FindPlanetReqModel
import com.gt.findingfalcon.dto.AppException
import com.gt.findingfalcon.dto.FindPlanetDto
import com.gt.findingfalcon.dto.PlanetDto
import com.gt.findingfalcon.dto.ResponseWrapper
import com.gt.findingfalcon.dto.TokenDto
import com.gt.findingfalcon.dto.VehiclesDto
import com.gt.findingfalcon.mapper.toFindPlanet
import com.gt.findingfalcon.mapper.toPlanet
import com.gt.findingfalcon.mapper.toVehicle
import com.gt.findingfalcon.model.FindPlanet
import com.gt.findingfalcon.model.Planet
import com.gt.findingfalcon.model.Vehicle
import com.gt.findingfalcon.network.ApiService
import com.xpressbees.core.wrappers.ApiStatus
import javax.inject.Inject

class PlanetRepo @Inject constructor(private val apiService: ApiService) : BaseRepo(),
    PlanetRepoListener {

    override suspend fun fetchPlanets(): ResponseWrapper<MutableList<Planet>, ApiStatus, AppException> {
        val planetsResponse = ResponseWrapper<MutableList<Planet>, ApiStatus, AppException>(
            response = mutableListOf(),
            apiStatus = ApiStatus.Loading,
            exception = null
        )
        val planetsApiResponse: ResponseWrapper<MutableList<PlanetDto>, ApiStatus, AppException> =
            apiWrapper {
                apiService.getPlanets()
            }
        if (planetsApiResponse.apiStatus == ApiStatus.Completed && !planetsApiResponse.response.isNullOrEmpty()) {
            for (planetsDto in planetsApiResponse.response!!) {
                planetsResponse.response?.add(toPlanet(planetsDto))
            }
            planetsResponse.apiStatus = ApiStatus.Completed
            planetsResponse.exception = null;
        }
        return planetsResponse
    }

    override suspend fun fetchVehicles(): ResponseWrapper<MutableList<Vehicle>, ApiStatus, AppException> {
        val vehicleResponse: ResponseWrapper<MutableList<Vehicle>, ApiStatus, AppException> =
            ResponseWrapper(
                response = mutableListOf(),
                apiStatus = ApiStatus.Loading,
                exception = null
            )

        val vehicleApiResponse: ResponseWrapper<MutableList<VehiclesDto>, ApiStatus, AppException> =
            apiWrapper {
                apiService.getVehicles()
            }

        if (vehicleApiResponse.apiStatus == ApiStatus.Completed && !vehicleApiResponse.response.isNullOrEmpty()) {
            for (vehicleDto in vehicleApiResponse.response!!) {
                vehicleResponse.response?.add(vehicleDto.toVehicle(vehicleDto))
            }
            vehicleResponse.apiStatus = ApiStatus.Completed
            vehicleResponse.exception = null;
        }
        return vehicleResponse
    }

    override suspend fun fetchToken(): ResponseWrapper<String, ApiStatus, AppException> {
        val tokenResponse: ResponseWrapper<String, ApiStatus, AppException> =
            ResponseWrapper(
                response = null,
                apiStatus = ApiStatus.Loading,
                exception = null
            )

        val tokenApiResponse: ResponseWrapper<TokenDto, ApiStatus, AppException> =
            apiWrapper {
                apiService.getToken()
            }

        if (tokenApiResponse.apiStatus == ApiStatus.Completed && !tokenApiResponse.response?.token.isNullOrEmpty()) {
            tokenResponse.response = tokenApiResponse.response?.token
            tokenResponse.apiStatus = ApiStatus.Completed
            tokenResponse.exception = null
        } else {
            tokenResponse.response = null
            tokenResponse.apiStatus = ApiStatus.Exception
            tokenResponse.exception = AppException.ExceptionWithMessage("Unable to proceed")
        }
        return tokenResponse
    }

    override suspend fun findPlanet(findPlanetReqModel: FindPlanetReqModel): ResponseWrapper<FindPlanet, ApiStatus, AppException> {
        val findPlanetResponse: ResponseWrapper<FindPlanet, ApiStatus, AppException> =
            ResponseWrapper(
                response = null,
                apiStatus = ApiStatus.Loading,
                exception = null
            )

        val findPlanetApiResponse: ResponseWrapper<FindPlanetDto, ApiStatus, AppException> =
            apiWrapper {
                apiService.findPlanet(payload = findPlanetReqModel)
            }

        if (findPlanetApiResponse.apiStatus == ApiStatus.Completed) {
            if (findPlanetApiResponse.response?.status.equals(
                    "success",
                    true
                )
            ) {
                findPlanetResponse.response =
                    toFindPlanet(findPlanetApiResponse.response!!)
                findPlanetResponse.apiStatus = ApiStatus.Completed
                findPlanetResponse.exception = null
            } else {
                findPlanetResponse.response = toFindPlanet(findPlanetApiResponse.response!!)
                findPlanetResponse.apiStatus = ApiStatus.Completed
                findPlanetResponse.exception =
                    AppException.ExceptionWithMessage("Planet not found")
            }
        }
        return findPlanetResponse

    }

}