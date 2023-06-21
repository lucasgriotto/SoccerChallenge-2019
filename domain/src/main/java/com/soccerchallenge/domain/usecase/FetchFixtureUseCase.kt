package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.SoccerRepository
import javax.inject.Inject

class FetchFixtureUseCase @Inject constructor(
        private val repository: SoccerRepository
) {

    suspend fun execute(forceRefresh: Boolean) = repository.fetchFixture(forceRefresh)

}
