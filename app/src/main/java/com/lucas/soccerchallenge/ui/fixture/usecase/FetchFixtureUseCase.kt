package com.lucas.soccerchallenge.ui.fixture.usecase

import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.ui.base.LoadableUseCase
import javax.inject.Inject

class FetchFixtureUseCase @Inject constructor(
    private val repository: SoccerRepository
) : LoadableUseCase<List<Match>, FetchFixtureUseCase.Params>() {

    override suspend fun doWork(params: Params): List<Match> =
        repository.fetchFixture(params.forceRefresh)

    data class Params(val forceRefresh: Boolean)

}
