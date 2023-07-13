package com.soccerchallenge.data.network.service

import com.soccerchallenge.data.network.model.MatchResponse
import retrofit2.http.GET

interface SoccerService {

    @GET("fixture")
    suspend fun fetchFixture(): List<MatchResponse>

    @GET("results")
    suspend fun fetchResults(): List<MatchResponse>

}