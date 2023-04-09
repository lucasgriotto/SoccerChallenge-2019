package com.lucas.soccerchallenge.core.data.datasource

import com.lucas.soccerchallenge.core.network.service.SoccerService
import com.lucas.soccerchallenge.core.network.utils.safeApiCall
import javax.inject.Inject

class RemoteSoccerDataSourceImpl @Inject constructor(
    private val service: SoccerService
) : RemoteSoccerDataSource {

    override suspend fun fetchFixture() = safeApiCall { service.getFixture() }

    override suspend fun fetchMatchResults() = safeApiCall { service.getResults() }

}
