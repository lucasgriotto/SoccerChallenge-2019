package com.lucas.soccerchallenge.repository

import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.model.Match
import kotlinx.coroutines.Job

interface SoccerRepository {

    fun getFixture(
        job: Job,
        onResult: (Resource<List<Match>>) -> Unit
    )

    fun getResults(
        job: Job,
        onResult: (Resource<List<Match>>) -> Unit
    )
}