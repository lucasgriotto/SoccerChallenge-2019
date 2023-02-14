package com.lucas.soccerchallenge.api

import com.lucas.soccerchallenge.data.MatchEntity
import retrofit2.Response
import retrofit2.http.GET

interface SoccerService {

    @GET("test-task/fixtures.json")
    suspend fun getFixture(): Response<List<MatchEntity>>

    @GET("test-task/results.json")
    suspend fun getResults(): Response<List<MatchEntity>>
}