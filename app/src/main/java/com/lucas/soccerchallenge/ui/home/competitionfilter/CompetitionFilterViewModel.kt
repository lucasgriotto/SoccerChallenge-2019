package com.lucas.soccerchallenge.ui.home.competitionfilter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.CompetitionFilterDisplayModel
import com.lucas.soccerchallenge.ui.home.competitionfilter.model.toFilterCompetitionDisplayModel
import com.soccerchallenge.data.util.CompetitionFilters
import com.soccerchallenge.data.util.CompetitionFilters.ALL_FILTER_INDEX
import com.soccerchallenge.domain.usecase.GetSelectedCompetitionsFilterIdsUseCase
import com.soccerchallenge.domain.usecase.SaveSelectedCompetitionFilterIdsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompetitionFilterViewModel @Inject constructor(
    private val getSelectedCompetitionsFilterIdsUseCase: GetSelectedCompetitionsFilterIdsUseCase,
    private val saveSelectedCompetitionFilterIdsUseCase: SaveSelectedCompetitionFilterIdsUseCase
) : ViewModel() {

    private val _filterIds = MutableSharedFlow<Set<Int>>()
    val filterIds = _filterIds.asSharedFlow()

    private val _competitionFiltersLoaded = MutableStateFlow(false)
    val competitionFiltersLoaded = _competitionFiltersLoaded.asStateFlow()

    lateinit var selectedFiltersIds: Set<Int>
        private set

    lateinit var allFilterCompetitionDisplayModels: List<CompetitionFilterDisplayModel>
        private set

    init {
        viewModelScope.launch {
            val selectedCompetitionsIds = getSelectedCompetitionsFilterIdsUseCase.execute()
            allFilterCompetitionDisplayModels = CompetitionFilters.competitionFilter.map { competition ->
                competition.toFilterCompetitionDisplayModel(
                    isSelected = selectedCompetitionsIds.contains(competition.id)
                )
            }
            selectedFiltersIds = selectedCompetitionsIds
            _competitionFiltersLoaded.emit(true)
        }
    }

    fun updateFilters(newFilters: List<CompetitionFilterDisplayModel>) {
        val filters = hashSetOf<Int>()
        newFilters.forEach { filter -> if (filter.isSelected) filters.add(filter.id) }

        // If all competitions are selected or no competition is selected is the same that selecting all, that logic
        // is handled here
        if (filters.size == CompetitionFilters.competitionFilter.size - 1) {
            filters.clear()
            filters.add(CompetitionFilters.allFilterCompetition.id)
            newFilters.forEachIndexed { index, filterCompetitionDisplayModel ->
                filterCompetitionDisplayModel.isSelected = index == ALL_FILTER_INDEX
            }
        } else if (filters.isEmpty()) {
            newFilters[ALL_FILTER_INDEX].isSelected = true
            filters.add(CompetitionFilters.allFilterCompetition.id)
        }

        selectedFiltersIds = filters
        allFilterCompetitionDisplayModels = newFilters
        viewModelScope.launch {
            _filterIds.emit(filters)
        }
        saveSelectedCompetitionsFilterIds()
    }

    private fun saveSelectedCompetitionsFilterIds() {
        viewModelScope.launch {
            saveSelectedCompetitionFilterIdsUseCase.execute(selectedFiltersIds)
        }
    }

}
