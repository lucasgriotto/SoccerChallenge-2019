package com.lucas.soccerchallenge.ui.results

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.results.adapter.ResultDisplayModel
import com.lucas.soccerchallenge.ui.results.adapter.toResultDisplayModel
import com.lucas.soccerchallenge.utils.Resource
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

    private val _results = MutableStateFlow<Resource<List<ResultDisplayModel>>>(Resource.Initialize())
    val results = _results.asStateFlow()

    fun fetchMatchResults(forceRefresh: Boolean) {
        _results.value = Resource.Loading()
        viewModelScope.launch {
            when (val response = fetchMatchResultsUseCase.execute(forceRefresh)) {
                is Response.Success -> {
                    val resultDisplayModels = response.data.map { it.toResultDisplayModel() }
                    _results.value = Resource.Success(resultDisplayModels)
                }

                is Response.Error -> _results.value = Resource.Error(ErrorFactory.getError(response.error))
            }
        }
    }

}
