package com.lucas.soccerchallenge.features.fixture

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.features.fixture.usecase.FetchFixtureUseCase
import javax.inject.Inject

class FixtureViewModel @Inject constructor(
    private val fetchFixtureUseCase: FetchFixtureUseCase
) : ViewModel() {

    val fixtureResponse = fetchFixtureUseCase.observe()

    init {
        fetchFixture()
    }

    fun fetchFixture() {
        fetchFixtureUseCase.execute(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        fetchFixtureUseCase.cancel()
    }
}
