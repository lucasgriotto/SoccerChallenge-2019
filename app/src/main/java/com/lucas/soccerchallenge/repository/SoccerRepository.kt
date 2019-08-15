package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.model.Match

interface SoccerRepository {

    suspend fun getFixture(onResult: (Resource<List<Match>>) -> Unit)

    suspend fun getResults(onResult: (Resource<List<Match>>) -> Unit)
}