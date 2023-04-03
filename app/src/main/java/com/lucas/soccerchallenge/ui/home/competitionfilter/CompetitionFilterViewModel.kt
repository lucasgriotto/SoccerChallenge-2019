package com.lucas.soccerchallenge.ui.home.competitionfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.core.model.Competition
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilters.ALL_FILTER_INDEX
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.FilterCompetitionDisplayModel
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.toCompetition
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.toFilterCompetitionDisplayModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompetitionFilterViewModel @Inject constructor() : ViewModel() {

    private val _filter = MutableSharedFlow<Set<Competition>>()
    val filter: SharedFlow<Set<Competition>> = _filter

    var selectedFilters = CompetitionFilters.defaultSelectedCompetition
        private set

    var allFilters = CompetitionFilters.competitionFilter.map { competition ->
        competition.toFilterCompetitionDisplayModel(
            isSelected = CompetitionFilters.defaultSelectedCompetition.contains(competition)
        )
    }
        private set

    fun updateFilters(newFilters: List<FilterCompetitionDisplayModel>) {
        val filters = hashSetOf<Competition>()
        newFilters.forEach { filter -> if (filter.isSelected) filters.add(filter.toCompetition()) }

        // If all competitions are selected or no competition is selected is the same that selecting all, that logic
        // is handled here
        if (filters.size == CompetitionFilters.competitionFilter.size - 1) {
            filters.clear()
            filters.add(CompetitionFilters.allFilterCompetition)
            newFilters.forEachIndexed { index, filterCompetitionDisplayModel ->
                filterCompetitionDisplayModel.isSelected = index == ALL_FILTER_INDEX
            }
        } else if (filters.isEmpty()) {
            newFilters[ALL_FILTER_INDEX].isSelected = true
            filters.add(CompetitionFilters.allFilterCompetition)
        }

        selectedFilters = filters
        allFilters = newFilters
        viewModelScope.launch {
            _filter.emit(filters)
        }
    }

}
