package com.lucas.soccerchallenge.features.results.usecase

import com.lucas.soccerchallenge.base.UseCase
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import javax.inject.Inject

class FetchMatchResultsUseCase @Inject constructor(
    private val repository: SoccerRepository
) : UseCase<List<Match>, Unit>() {

    override suspend fun getData(e: Unit): List<Match> {
        return repository.fetchMatchResults()
    }

}