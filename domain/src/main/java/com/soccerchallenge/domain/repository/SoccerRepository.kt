package com.soccerchallenge.domain.repository

import com.soccerchallenge.domain.model.Match
import com.soccerchallenge.domain.util.Response

interface SoccerRepository {

    suspend fun fetchFixture(forceRefresh: Boolean): Response<List<Match>>

    suspend fun fetchMatchResults(forceRefresh: Boolean): Response<List<Match>>

    suspend fun fetchMatch(matchId: Int): Response<Match>

}