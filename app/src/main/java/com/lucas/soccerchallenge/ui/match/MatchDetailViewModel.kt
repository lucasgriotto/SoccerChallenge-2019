package com.lucas.soccerchallenge.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.core.model.MatchType
import com.lucas.soccerchallenge.ui.base.Resource
import com.lucas.soccerchallenge.ui.fixture.adapter.FixtureDisplayModel
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.match.usecase.FetchMatchUseCase
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchDetailViewModel @Inject constructor(
    private val fetchMatchUseCase: FetchMatchUseCase
) : ViewModel() {

    private val _fixtureResponse = MutableStateFlow<Resource<FixtureDisplayModel>>(Resource.Initialize())
    val fixtureResponse: StateFlow<Resource<FixtureDisplayModel>> = _fixtureResponse

    private val _resultResponse = MutableStateFlow<Resource<ResultDisplayModel>>(Resource.Initialize())
    val resultResponse: StateFlow<Resource<ResultDisplayModel>> = _resultResponse

    init {
        viewModelScope.launch {
            fetchMatchUseCase.result.collect { response ->
                if (response is Resource.Success) {
                    val match = response.data
                    when (match.type) {
                        MatchType.FIXTURE -> {
                            _fixtureResponse.value = Resource.Success(match.toFixtureDisplayModel())
                        }
                        MatchType.RESULT -> {
                            _resultResponse.value = Resource.Success(match.toResultDisplayModel())
                        }
                    }
                }
            }
        }
    }

    fun fetchMatch(matchId: Int) {
        fetchMatchUseCase.execute(viewModelScope, FetchMatchUseCase.Params(matchId))
    }

}
