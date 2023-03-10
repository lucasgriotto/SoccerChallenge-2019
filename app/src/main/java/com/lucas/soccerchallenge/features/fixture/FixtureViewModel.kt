package com.lucas.soccerchallenge.features.fixture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.features.fixture.usecase.FetchFixtureUseCase
import javax.inject.Inject

class FixtureViewModel @Inject constructor(
    private val fetchFixtureUseCase: FetchFixtureUseCase
) : ViewModel() {

    val fixtureResponse = fetchFixtureUseCase.result

    init {
        fetchFixture()
    }

    fun fetchFixture() {
        fetchFixtureUseCase.execute(viewModelScope, Unit)
    }

}
