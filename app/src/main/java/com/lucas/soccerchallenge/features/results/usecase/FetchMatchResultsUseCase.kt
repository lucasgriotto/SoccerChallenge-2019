package com.lucas.soccerchallenge.features.results.usecase

import com.lucas.soccerchallenge.core.base.LoadableUseCase
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import javax.inject.Inject

class FetchMatchResultsUseCase @Inject constructor(
    private val repository: SoccerRepository
) : LoadableUseCase<List<Match>, Unit>() {

    override fun getFlow(params: Unit) = repository.fetchMatchResults()

}
