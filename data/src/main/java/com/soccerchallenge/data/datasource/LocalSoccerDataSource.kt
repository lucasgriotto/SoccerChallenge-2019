package com.soccerchallenge.data.datasource

import com.soccerchallenge.data.database.entity.MatchEntity
import com.soccerchallenge.domain.model.MatchType

interface LocalSoccerDataSource {

    suspend fun fetchFixtureFromLocal(): List<MatchEntity>

    suspend fun fetchMatchResultsFromLocal(): List<MatchEntity>

    suspend fun insertMatches(matches: List<MatchEntity>)

    suspend fun deleteMatches(matchType: MatchType)

    suspend fun fetchMatchFromLocal(matchId: Int): MatchEntity

}