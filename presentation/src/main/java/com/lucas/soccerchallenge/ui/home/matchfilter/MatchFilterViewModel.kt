package com.lucas.soccerchallenge.ui.home.matchfilter

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchHeaderDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchItemDisplayModel
import com.lucas.soccerchallenge.utils.DateUtils
import com.soccerchallenge.data.di.qualifier.DefaultDispatcher
import com.soccerchallenge.data.util.CompetitionFilters
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchFilterViewModel @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var allMatches: List<MatchDisplayModel> = emptyList()

    private val _filteredMatches = MutableStateFlow<List<MatchItemDisplayModel>>(emptyList())
    val filteredMatches = _filteredMatches.asStateFlow()

    suspend fun filterMatches(
        newMatches: List<MatchDisplayModel>? = null,
        filtersIds: Set<Int>
    ) {
        withContext(dispatcher) {
            newMatches?.let { matches ->
                allMatches = matches
            }
            val filtered =
                if (filtersIds.size == 1 && filtersIds.contains(CompetitionFilters.allFilterCompetition.id)) {
                    allMatches
                } else {
                    allMatches.filter { filtersIds.contains(it.competitionId) }
                }

            val matchWithHeaders = mutableListOf<MatchItemDisplayModel>()

            filtered.forEachIndexed { index, match ->
                val previousMatch = filtered.getOrNull(index - 1)
                if (previousMatch == null || !DateUtils.isSameMonthYear(
                        previousMatch.date,
                        match.date
                    )
                ) {
                    val date = DateUtils.getMonthYear(match.date)
                    matchWithHeaders.add(MatchHeaderDisplayModel(date))
                }
                matchWithHeaders.add(match as MatchItemDisplayModel)
            }

            _filteredMatches.value = matchWithHeaders
        }
    }

}
