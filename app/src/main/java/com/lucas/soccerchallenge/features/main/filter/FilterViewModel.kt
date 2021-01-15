package com.lucas.soccerchallenge.features.main.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.data.model.Competition
import javax.inject.Inject

class FilterViewModel @Inject constructor() : ViewModel() {

    private val _filter = MutableLiveData<HashSet<Competition>>()
    val filter: LiveData<HashSet<Competition>> = _filter

    var backedUpFilter = hashSetOf<Competition>()
        private set

    fun setFilters(filter: HashSet<Competition>) {
        _filter.postValue(filter)
    }

    fun getFilters() = _filter.value

    fun backUpSelectedFilters(filters: HashSet<Competition>) {
        backedUpFilter = filters.toHashSet()
    }
}