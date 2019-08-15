package com.lucas.soccerchallenge.features.fixture

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.features.fixture.usecase.GetFixtureUseCase
import javax.inject.Inject

class FixtureViewModel @Inject
constructor(private val getFixtureUseCase: GetFixtureUseCase) : ViewModel() {

    val getFixtureResponse = getFixtureUseCase.observe()

    init {
        getFixture()
    }

    fun getFixture(){
        getFixtureUseCase.execute(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        getFixtureUseCase.cancel()
    }
}
