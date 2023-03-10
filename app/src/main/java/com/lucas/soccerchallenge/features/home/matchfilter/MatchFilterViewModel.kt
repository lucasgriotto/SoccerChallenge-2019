package com.lucas.soccerchallenge.features.home.matchfilter

import androidx.lifecycle.ViewModel
import com.lucas.soccerchallenge.data.Competition
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.di.qualifier.DefaultDispatcher
import com.lucas.soccerchallenge.features.home.competitionfilter.CompetitionFilters
import com.lucas.soccerchallenge.features.home.matchfilter.model.MatchHeaderDisplayModel
import com.lucas.soccerchallenge.features.home.matchfilter.model.MatchItemDisplayModel
import com.lucas.soccerchallenge.utils.DateUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MatchFilterViewModel @Inject constructor(
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var allMatches: List<Match> = emptyList()

    private val _filteredMatches = MutableStateFlow<List<MatchItemDisplayModel>>(emptyList())
    val filteredMatches: StateFlow<List<MatchItemDisplayModel>> = _filteredMatches

    suspend fun filterMatches(
        newMatches: List<Match>? = null,
        filters: Set<Competition>,
        matchMapper: (Match) -> MatchItemDisplayModel
    ) {
        withContext(dispatcher) {
            newMatches?.let { matches ->
                allMatches = matches
            }
            val filtered = if (filters.size == 1 && filters.contains(CompetitionFilters.allFilterCompetition))
                allMatches
            else
                allMatches.filter { filters.contains(it.competition) }

            val matchWithHeaders = mutableListOf<MatchItemDisplayModel>()

            filtered.forEachIndexed { index, match ->
                val previousMatch = filtered.getOrNull(index - 1)
                if (previousMatch == null || !DateUtils.isSameMonthYear(previousMatch.date, match.date)) {
                    val date = DateUtils.getMonthYear(match.date)
                    matchWithHeaders.add(MatchHeaderDisplayModel(date))
                }
                matchWithHeaders.add(matchMapper(match))
            }

            _filteredMatches.value = matchWithHeaders
        }
    }

}
