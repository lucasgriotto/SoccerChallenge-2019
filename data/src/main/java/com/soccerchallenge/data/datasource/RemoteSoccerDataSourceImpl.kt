package com.soccerchallenge.data.datasource

import com.soccerchallenge.data.network.service.SoccerService
import javax.inject.Inject

class RemoteSoccerDataSourceImpl @Inject constructor(
    private val service: SoccerService
) : RemoteSoccerDataSource {

    override suspend fun fetchFixture() = service.getFixture()

    override suspend fun fetchMatchResults() = service.getResults()

}