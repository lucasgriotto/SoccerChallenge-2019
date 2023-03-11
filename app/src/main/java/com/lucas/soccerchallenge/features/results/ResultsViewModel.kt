package com.lucas.soccerchallenge.features.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.features.results.usecase.FetchMatchResultsUseCase
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
    private val fetchMatchResultsUseCase: FetchMatchResultsUseCase
) : ViewModel() {

    val matchResultsResponse = fetchMatchResultsUseCase.result

    init {
        fetchMatchResults()
    }

    fun fetchMatchResults() {
        fetchMatchResultsUseCase.execute(viewModelScope, Unit)
    }

}
