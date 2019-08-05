package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.base.networking.ApiServiceExecutor
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.entities.MatchEntity
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.toMatch
import kotlinx.coroutines.Job
import javax.inject.Inject

class SoccerRepositoryImpl @Inject
constructor(private val service: SoccerService) : SoccerRepository {

    override fun getFixture(job: Job, onResult: (Resource<List<Match>>) -> Unit) {
        object : ApiServiceExecutor<List<MatchEntity>>(onResult) {

            override fun onSuccess(result: List<MatchEntity>) {
                val matchList = result.map {
                    it.toMatch()
                }
                onResult(Resource.Success(matchList))
            }

            override suspend fun getCall() = service.getFixture()
        }.execute(job)
    }

    override fun getResults(job: Job, onResult: (Resource<List<Match>>) -> Unit) {
        object : ApiServiceExecutor<List<MatchEntity>>(onResult) {

            override fun onSuccess(result: List<MatchEntity>) {
                val matchList = result.map {
                    it.toMatch()
                }
                onResult(Resource.Success(matchList))
            }

            override suspend fun getCall() = service.getResults()
        }.execute(job)
    }
}