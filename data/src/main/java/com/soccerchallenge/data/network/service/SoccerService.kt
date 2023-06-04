package com.soccerchallenge.data.network.service

import com.soccerchallenge.data.network.model.MatchResponse
import retrofit2.http.GET

interface SoccerService {

    @GET("fixtures.json")
    suspend fun getFixture(): List<MatchResponse>

    @GET("results.json")
    suspend fun getResults(): List<MatchResponse>

}