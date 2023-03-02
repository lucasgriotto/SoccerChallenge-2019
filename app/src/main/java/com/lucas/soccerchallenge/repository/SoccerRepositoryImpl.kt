package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.core.extension.suspendApiCallWrapper
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.data.toMatch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SoccerRepositoryImpl @Inject constructor(
    private val service: SoccerService
) : SoccerRepository {

    override suspend fun fetchFixture(): List<Match> {
        val fixture = suspendApiCallWrapper {
            service.getFixture()
        }
        return withContext(Dispatchers.Default) {
            fixture.map { it.toMatch() }
        }
    }

    override suspend fun fetchMatchResults(): List<Match> {
        val matches = suspendApiCallWrapper {
            service.getResults()
        }
        return withContext(Dispatchers.Default) {
            matches.map { it.toMatch() }
        }
    }

}
