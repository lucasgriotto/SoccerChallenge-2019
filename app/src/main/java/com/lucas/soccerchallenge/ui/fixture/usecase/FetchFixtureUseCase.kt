package com.lucas.soccerchallenge.ui.fixture.usecase

import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.ui.base.LoadableUseCase
import javax.inject.Inject

class FetchFixtureUseCase @Inject constructor(
    private val repository: SoccerRepository
) : LoadableUseCase<List<Match>, Unit>() {

    override fun getFlow(params: Unit) = repository.fetchFixture()

}
