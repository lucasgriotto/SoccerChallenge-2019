package com.soccerchallenge.data.datasource

import com.soccerchallenge.data.network.model.MatchResponse

interface RemoteSoccerDataSource {

    suspend fun fetchFixture(): List<MatchResponse>

    suspend fun fetchMatchResults(): List<MatchResponse>

}