package com.lucas.soccerchallenge.features.fixture

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.base.networking.Resource
import com.lucas.soccerchallenge.data.model.Match
import com.lucas.soccerchallenge.repository.SoccerRepository
import kotlinx.coroutines.Job
import javax.inject.Inject

class FixtureViewModel @Inject
constructor(private val repository: SoccerRepository) : ViewModel() {

    private val job = Job()

    val getFixtureResponse = MutableLiveData<Resource<List<Match>>>()

    init {
        getFixture()
    }

    fun getFixture(){
        repository.getFixture(job) { response ->
            when (response) {
                is Resource.Loading ->
                    getFixtureResponse.postValue(Resource.Loading())
                is Resource.Error ->
                    getFixtureResponse.postValue(Resource.Error(response.message))
                is Resource.Success ->
                    getFixtureResponse.postValue(Resource.Success(response.data))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}
