package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.SoccerRepository
import javax.inject.Inject

class FetchMatchResultsUseCase @Inject constructor(
    private val repository: SoccerRepository
) {

    suspend fun execute(forceRefresh: Boolean) = repository.fetchMatchResults(forceRefresh)

}