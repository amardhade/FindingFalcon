package com.gt.findingfalcon.network

import com.google.gson.JsonObject
import com.gt.findingfalcon.FindPlanetReqModel
import com.gt.findingfalcon.dto.FindPlanetDto
import com.gt.findingfalcon.dto.PlanetDto
import com.gt.findingfalcon.dto.TokenDto
import com.gt.findingfalcon.dto.VehiclesDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getPlanets(@Url hostUrl: String = "https://findfalcone.geektrust.com/planets"): Response<MutableList<PlanetDto>>

    @GET
    suspend fun getVehicles(@Url hostUrl: String = "https://findfalcone.geektrust.com/vehicles"): Response<MutableList<VehiclesDto>>

    @Headers("Accept: application/json")
    @POST
    suspend fun getToken(
        @Url hostUrl: String = "https://ﬁndfalcone.geektrust.com/token",
        @Body payload: JsonObject = JsonObject()
    ): Response<TokenDto>

    @Headers("Accept: application/json")
    @POST
    suspend fun findPlanet(
        @Url hostUrl: String = "https://ﬁndfalcone.geektrust.com/find",
        @Body payload: FindPlanetReqModel
    ): Response<FindPlanetDto>


}