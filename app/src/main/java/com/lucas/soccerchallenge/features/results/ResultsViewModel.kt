package com.lucas.soccerchallenge.features.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

class ResultsViewModel @Inject
constructor(private val repository: SoccerRepository) : ViewModel() {

    private val job = Job()

    val getResultsResponse = MutableLiveData<Resource<List<Match>>>()

    init {
        getResults()
    }

    fun getResults(){
        repository.getResults(job) { response ->
            when (response) {
                is Resource.Loading ->
                    getResultsResponse.postValue(Resource.Loading())
                is Resource.Error ->
                    getResultsResponse.postValue(Resource.Error(response.message))
                is Resource.Success ->
                    getResultsResponse.postValue(Resource.Success(response.data))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}
