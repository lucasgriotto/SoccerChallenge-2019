package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.api.SoccerService
import com.lucas.soccerchallenge.base.networking.ApiResponseWrapper
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.entities.MatchEntity
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.data.toMatch
import javax.inject.Inject

class SoccerRepositoryImpl @Inject
constructor(private val service: SoccerService) : SoccerRepository {

    override suspend fun getFixture(onResult: (Resource<List<Match>>) -> Unit) {
        object : ApiResponseWrapper<List<MatchEntity>>(onResult) {

            override fun onSuccess(result: List<MatchEntity>) {
                val matchList = result.map {
                    it.toMatch()
                }
                onResult(Resource.Success(matchList))
            }

            override suspend fun getCall() = service.getFixture()
        }.getData()
    } 

    override suspend fun getResults(onResult: (Resource<List<Match>>) -> Unit) {
        object : ApiResponseWrapper<List<MatchEntity>>(onResult) {

            override fun onSuccess(result: List<MatchEntity>) {
                val matchList = result.map {
                    it.toMatch()
                }
                onResult(Resource.Success(matchList))
            }

            override suspend fun getCall() = service.getResults()
        }.getData()
    }
}