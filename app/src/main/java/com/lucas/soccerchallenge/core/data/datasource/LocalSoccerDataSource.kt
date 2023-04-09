package com.lucas.soccerchallenge.core.data.datasource

import com.lucas.soccerchallenge.core.database.entity.MatchEntity
import com.lucas.soccerchallenge.core.model.MatchType

interface LocalSoccerDataSource {

    suspend fun fetchFixtureFromLocal(): List<MatchEntity>

    suspend fun fetchMatchResultsFromLocal(): List<MatchEntity>

    suspend fun insertMatches(matches: List<MatchEntity>)

    suspend fun deleteMatches(matchType: MatchType)

}