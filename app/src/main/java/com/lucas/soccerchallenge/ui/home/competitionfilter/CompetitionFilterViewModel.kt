package com.lucas.soccerchallenge.ui.home.competitionfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.core.data.repository.UserPreferencesRepository
import com.lucas.soccerchallenge.core.model.Competition
import com.lucas.soccerchallenge.ui.home.competitionfilter.CompetitionFilters.ALL_FILTER_INDEX
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.FilterCompetitionDisplayModel
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.toCompetition
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.toFilterCompetitionDisplayModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompetitionFilterViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _filter = MutableSharedFlow<Set<Competition>>()
    val filter = _filter.asSharedFlow()

    private val _competitionFiltersLoaded = MutableStateFlow(false)
    val competitionFiltersLoaded = _competitionFiltersLoaded.asStateFlow()

    lateinit var selectedFilters: Set<Competition>
        private set

    lateinit var allFilterCompetitionDisplayModels: List<FilterCompetitionDisplayModel>
        private set

    init {
        viewModelScope.launch {
            val selectedCompetitionsIds = userPreferencesRepository.getSelectedCompetitionsFilterIds()
            allFilterCompetitionDisplayModels = CompetitionFilters.competitionFilter.map { competition ->
                competition.toFilterCompetitionDisplayModel(
                    isSelected = selectedCompetitionsIds.contains(competition.id)
                )
            }
            selectedFilters = CompetitionFilters.getCompetitionsFromIds(selectedCompetitionsIds)
            _competitionFiltersLoaded.emit(true)
        }
    }

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
        allFilterCompetitionDisplayModels = newFilters
        viewModelScope.launch {
            _filter.emit(filters)
        }
        saveSelectedCompetitionsFilterIds()
    }

    private fun saveSelectedCompetitionsFilterIds() {
        viewModelScope.launch {
            val selectedFiltersIds = selectedFilters.map { it.id }.toSet()
            userPreferencesRepository.saveSelectedCompetitionsFilterIds(selectedFiltersIds)
        }
    }

}
