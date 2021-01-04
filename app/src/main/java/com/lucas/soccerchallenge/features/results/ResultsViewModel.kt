package com.lucas.soccerchallenge.features.results

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.features.results.usecase.FetchMatchResultsUseCase
import javax.inject.Inject

class ResultsViewModel @Inject
constructor(private val fetchMatchResultsUseCase: FetchMatchResultsUseCase) : ViewModel() {

    val getResultResponse = fetchMatchResultsUseCase.observe()

    init {
        fetchMatchResults()
    }

    fun fetchMatchResults() {
        fetchMatchResultsUseCase.execute(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        fetchMatchResultsUseCase.cancel()
    }
}
