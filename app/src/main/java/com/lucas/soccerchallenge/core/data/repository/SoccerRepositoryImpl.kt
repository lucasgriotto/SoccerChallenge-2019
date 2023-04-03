package com.lucas.soccerchallenge.core.data.repository

import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.core.network.model.mapper.toMatch
import com.lucas.soccerchallenge.core.network.service.SoccerService
import com.lucas.soccerchallenge.core.network.utils.safeApiCall
import com.lucas.soccerchallenge.di.qualifier.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SoccerRepositoryImpl @Inject constructor(
    private val service: SoccerService,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : SoccerRepository {

    override fun fetchFixture(): Flow<List<Match>> = flow {
        val fixture = safeApiCall { service.getFixture() }
            .map { it.toMatch() }
        emit(fixture)
    }.flowOn(dispatcher)

    override fun fetchMatchResults(): Flow<List<Match>> = flow {
        val results = safeApiCall { service.getResults() }
            .map { it.toMatch() }
        emit(results)
    }.flowOn(dispatcher)

}