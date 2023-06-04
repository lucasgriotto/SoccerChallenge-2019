package com.soccerchallenge.domain.usecase

import com.soccerchallenge.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveSelectedCompetitionFilterIdsUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {

    suspend fun execute(ids: Set<Int>) = repository.saveSelectedCompetitionsFilterIds(ids)

}
