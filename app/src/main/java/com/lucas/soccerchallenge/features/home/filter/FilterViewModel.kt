package com.lucas.soccerchallenge.features.home.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.data.Competition
import javax.inject.Inject

class FilterViewModel @Inject constructor() : ViewModel() {

    private val _filter = MutableLiveData<Set<Competition>>()
    val filter: LiveData<Set<Competition>> = _filter

    fun setFilters(filter: Set<Competition>) {
        _filter.postValue(filter)
    }

}
