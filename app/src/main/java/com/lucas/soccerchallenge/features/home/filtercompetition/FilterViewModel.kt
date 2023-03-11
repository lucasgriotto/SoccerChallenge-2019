package com.lucas.soccerchallenge.features.home.filtercompetition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.data.Competition
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor() : ViewModel() {

    private val _filter = MutableSharedFlow<Set<Competition>>()
    val filter: SharedFlow<Set<Competition>> = _filter
    var currentFilter = Filters.defaultSelectedCompetition

    fun setFilters(filter: Set<Competition>) {
        currentFilter = filter
        viewModelScope.launch {
            _filter.emit(filter)
        }
    }

}
