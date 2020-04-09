package com.lucas.soccerchallenge.features.fixture.usecase

import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.base.ui.LiveDataUseCase
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import javax.inject.Inject

class GetFixtureUseCase @Inject
constructor(private val repository: SoccerRepository) : LiveDataUseCase<Unit, Resource<List<Match>>>() {

    override suspend fun getData(params: Unit) {
        repository.getFixture { response ->
            result.postValue(response)
        }
    }
}