package com.lucas.soccerchallenge.features.results

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.features.results.usecase.GetResultsUseCase
import javax.inject.Inject

class ResultsViewModel @Inject
constructor(private val getResultsUseCase: GetResultsUseCase) : ViewModel() {

    val getResultResponse = getResultsUseCase.observe()

    init {
        getResults()
    }

    fun getResults() {
        getResultsUseCase.execute(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        getResultsUseCase.cancel()
    }
}
