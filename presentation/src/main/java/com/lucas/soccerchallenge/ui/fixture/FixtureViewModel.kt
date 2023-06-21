package com.lucas.soccerchallenge.ui.fixture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.home.match.MatchScreenState
import com.lucas.soccerchallenge.utils.errorfactory.ErrorFactory
import com.soccerchallenge.domain.usecase.FetchFixtureUseCase
import com.soccerchallenge.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FixtureViewModel @Inject constructor(
        private val fetchFixtureUseCase: FetchFixtureUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MatchScreenState>(MatchScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchFixture(forceRefresh: Boolean) {
        _uiState.value = MatchScreenState.Loading
        viewModelScope.launch {
            when (val response = fetchFixtureUseCase.execute(forceRefresh)) {
                is Response.Success -> {
                    val fixtureDisplayModels = response.data.map { it.toFixtureDisplayModel() }
                    _uiState.value = MatchScreenState.Success(fixtureDisplayModels)
                }

                is Response.Error -> _uiState.value = MatchScreenState.Error(ErrorFactory.getError(response.error))
            }
        }
    }

}
