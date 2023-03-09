package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.data.Match
import kotlinx.coroutines.flow.Flow

interface SoccerRepository {

    fun fetchFixture(): Flow<List<Match>>

    fun fetchMatchResults(): Flow<List<Match>>

}
