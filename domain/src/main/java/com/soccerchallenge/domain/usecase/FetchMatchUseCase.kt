package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.SoccerRepository
import javax.inject.Inject

class FetchMatchUseCase @Inject constructor(
        private val repository: SoccerRepository
) {

    suspend fun execute(matchId: Int) = repository.fetchMatch(matchId)

}