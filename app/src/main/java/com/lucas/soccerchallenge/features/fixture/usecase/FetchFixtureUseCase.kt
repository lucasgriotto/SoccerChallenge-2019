package com.lucas.soccerchallenge.features.fixture.usecase

import com.lucas.soccerchallenge.core.base.UseCase
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import javax.inject.Inject

class FetchFixtureUseCase @Inject constructor(
    private val repository: SoccerRepository
) : UseCase<List<Match>, Unit>() {

    override fun getFlow(params: Unit) = repository.fetchFixture()

}
