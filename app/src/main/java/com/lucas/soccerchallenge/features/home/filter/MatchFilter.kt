package com.lucas.soccerchallenge.features.home.filter

import com.lucas.soccerchallenge.data.Match
import javax.inject.Inject

class MatchFilter @Inject constructor() {

    var matches: List<Match> = emptyList()

    val filteredMatches = mutableListOf<Match>()

    var filters = Filters.defaultSelectedCompetition

    fun setFilteredMatches(matches: List<Match>) {
        filteredMatches.clear()
        filteredMatches.addAll(matches)
    }

    fun getFilteredMatches(matches: List<Match>): List<Match> {
        return if (filters.size == 1 && filters.contains(Filters.allFilterCompetition))
            matches
        else
            matches.filter { filters.contains(it.competition) }
    }
}