package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.core.extension.safeApiCall
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.data.toMatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SoccerRepositoryImpl @Inject constructor(
    private val service: SoccerService
) : SoccerRepository {

    override fun fetchFixture(): Flow<List<Match>> = flow {
        val fixture = safeApiCall { service.getFixture() }
            .map { it.toMatch() }
        emit(fixture)
    }.flowOn(Dispatchers.Default)

    override fun fetchMatchResults(): Flow<List<Match>> = flow {
        val results = safeApiCall { service.getResults() }
            .map { it.toMatch() }
        emit(results)
    }.flowOn(Dispatchers.Default)

}
