package com.lucas.soccerchallenge.ui.match.usecase

import com.lucas.soccerchallenge.core.data.repository.SoccerRepository
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.ui.base.LoadableUseCase
import javax.inject.Inject

class FetchMatchUseCase @Inject constructor(
    private val repository: SoccerRepository
) : LoadableUseCase<Match, FetchMatchUseCase.Params>() {

    override suspend fun doWork(params: Params) = repository.fetchMatch(params.matchId)

    data class Params(val matchId: Int)

}
