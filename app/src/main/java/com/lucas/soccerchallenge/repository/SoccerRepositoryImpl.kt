package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.base.suspendApiCallWrapper
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.toMatch
import javax.inject.Inject

class SoccerRepositoryImpl @Inject constructor(
    private val service: SoccerService
) : SoccerRepository {

    override suspend fun fetchFixture(): List<Match> {
        return suspendApiCallWrapper {
            service.getFixture()
        }.map { it.toMatch() }
    }

    override suspend fun fetchMatchResults(): List<Match> {
        return suspendApiCallWrapper {
            service.getResults()
        }.map { it.toMatch() }
    }

}