package com.lucas.soccerchallenge.features.home.filtercompetition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.data.Competition
import com.lucas.soccerchallenge.features.home.filtercompetition.Filters.ALL_FILTER_INDEX
import com.lucas.soccerchallenge.features.home.filtercompetition.model.FilterCompetitionDisplayModel
import com.lucas.soccerchallenge.features.home.filtercompetition.model.toCompetition
import com.lucas.soccerchallenge.features.home.filtercompetition.model.toFilterCompetitionDisplayModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilterViewModel @Inject constructor() : ViewModel() {

    private val _filter = MutableSharedFlow<Set<Competition>>()
    val filter: SharedFlow<Set<Competition>> = _filter

    var selectedFilters = Filters.defaultSelectedCompetition
        private set

    var allFilters = Filters.competitionFilter.map { competition ->
        competition.toFilterCompetitionDisplayModel(Filters.defaultSelectedCompetition.contains(competition))
    }
        private set

    fun updateFilters(newFilters: List<FilterCompetitionDisplayModel>) {
        val filters = hashSetOf<Competition>()
        newFilters.forEach { filter -> if (filter.isSelected) filters.add(filter.toCompetition()) }

        // If all competitions are selected or no competition is selected is the same that selecting all, that logic
        // is handled here
        if (filters.size == Filters.competitionFilter.size - 1) {
            filters.clear()
            filters.add(Filters.allFilterCompetition)
            newFilters.forEachIndexed { index, filterCompetitionDisplayModel ->
                filterCompetitionDisplayModel.isSelected = index == ALL_FILTER_INDEX
            }
        } else if (filters.isEmpty()) {
            newFilters[ALL_FILTER_INDEX].isSelected = true
            filters.add(Filters.allFilterCompetition)
        }

        selectedFilters = filters
        allFilters = newFilters
        viewModelScope.launch {
            _filter.emit(filters)
        }
    }

}
