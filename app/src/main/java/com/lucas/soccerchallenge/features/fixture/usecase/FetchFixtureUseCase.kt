package com.lucas.soccerchallenge.features.fixture.usecase

import com.lucas.soccerchallenge.core.base.LoadableUseCase
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import javax.inject.Inject

class FetchFixtureUseCase @Inject constructor(
    private val repository: SoccerRepository
) : LoadableUseCase<List<Match>, Unit>() {

    override fun getFlow(params: Unit) = repository.fetchFixture()

}
