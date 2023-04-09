package com.lucas.soccerchallenge.core.data.repository

import com.lucas.soccerchallenge.core.model.Match

interface SoccerRepository {

    suspend fun fetchFixture(forceRefresh: Boolean): List<Match>

    suspend fun fetchMatchResults(forceRefresh: Boolean): List<Match>

}
