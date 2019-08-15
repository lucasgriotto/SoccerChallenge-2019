package com.lucas.soccerchallenge.features.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.data.model.Competition
import javax.inject.Inject

class FilterDialogViewModel @Inject
constructor() : ViewModel() {

    private val _filter = MutableLiveData<HashSet<Competition>>()
    val filter: LiveData<HashSet<Competition>> = _filter

    fun setFilters(filter: HashSet<Competition>) {
        _filter.postValue(if (filter.isNotEmpty()) filter else null)
    }

    fun getFilters() = _filter.value
}
