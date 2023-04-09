package com.lucas.soccerchallenge.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.results.usecase.FetchMatchResultsUseCase
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
    private val fetchMatchResultsUseCase: FetchMatchResultsUseCase
) : ViewModel() {

    val matchResultsResponse = fetchMatchResultsUseCase.result

    init {
        fetchMatchResults(false)
    }

    fun fetchMatchResults(forceRefresh: Boolean) {
        fetchMatchResultsUseCase.execute(viewModelScope, FetchMatchResultsUseCase.Params(forceRefresh))
    }

}
