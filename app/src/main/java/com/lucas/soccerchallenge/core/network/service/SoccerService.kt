package com.lucas.soccerchallenge.core.network.service

import com.lucas.soccerchallenge.core.network.model.MatchResponse
import retrofit2.Response
import retrofit2.http.GET

interface SoccerService {

    @GET("test-task/fixtures.json")
    suspend fun getFixture(): Response<List<MatchResponse>>

    @GET("test-task/results.json")
    suspend fun getResults(): Response<List<MatchResponse>>
}