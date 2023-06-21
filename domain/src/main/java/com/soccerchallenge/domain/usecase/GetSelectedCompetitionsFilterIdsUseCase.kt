package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class GetSelectedCompetitionsFilterIdsUseCase @Inject constructor(
        private val repository: UserPreferencesRepository
) {

    suspend fun execute() = repository.getSelectedCompetitionsFilterIds()

}
