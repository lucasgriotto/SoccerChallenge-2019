package com.lucas.soccerchallenge.core.data.datasource

import com.lucas.soccerchallenge.core.network.model.MatchResponse

interface RemoteSoccerDataSource {

    suspend fun fetchFixture(): List<MatchResponse>

    suspend fun fetchMatchResults(): List<MatchResponse>
}