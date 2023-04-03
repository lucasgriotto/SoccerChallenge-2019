package com.lucas.soccerchallenge.core.data.repository

import com.lucas.soccerchallenge.core.model.Match
import kotlinx.coroutines.flow.Flow

interface SoccerRepository {

    fun fetchFixture(): Flow<List<Match>>

    fun fetchMatchResults(): Flow<List<Match>>

}