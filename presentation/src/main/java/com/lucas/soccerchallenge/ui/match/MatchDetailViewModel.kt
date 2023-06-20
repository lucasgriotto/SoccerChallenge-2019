package com.lucas.soccerchallenge.ui.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.fixture.adapter.toFixtureDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.soccerchallenge.domain.model.MatchType
import com.soccerchallenge.domain.usecase.FetchMatchUseCase
import com.soccerchallenge.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchDetailViewModel @Inject constructor(
    private val fetchMatchUseCase: FetchMatchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MatchDetailScreenState>(MatchDetailScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchMatch(matchId: Int) {
        viewModelScope.launch {
            val response = fetchMatchUseCase.execute(matchId)
            if (response is Response.Success) {
                val match = response.data
                when (match.type) {
                    MatchType.FIXTURE -> {
                        _uiState.value = MatchDetailScreenState.FixtureData(match.toFixtureDisplayModel())
                    }

                    MatchType.RESULT -> {
                        _uiState.value = MatchDetailScreenState.ResultData(match.toResultDisplayModel())
                    }
                }
            }
        }
    }

}
