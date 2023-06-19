package com.lucas.soccerchallenge.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.home.match.MatchScreenState
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.errorfactory.ErrorFactory
import com.soccerchallenge.domain.usecase.FetchMatchResultsUseCase
import com.soccerchallenge.domain.util.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
    private val fetchMatchResultsUseCase: FetchMatchResultsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MatchScreenState>(MatchScreenState.Loading)
    val uiState = _uiState.asStateFlow()

    fun fetchMatchResults(forceRefresh: Boolean) {
        _uiState.value = MatchScreenState.Loading
        viewModelScope.launch {
            when (val response = fetchMatchResultsUseCase.execute(forceRefresh)) {
                is Response.Success -> {
                    val resultDisplayModels = response.data.map { it.toResultDisplayModel() }
                    _uiState.value = MatchScreenState.Success(resultDisplayModels)
                }

                is Response.Error -> _uiState.value = MatchScreenState.Error(ErrorFactory.getError(response.error))
            }
        }
    }

}
