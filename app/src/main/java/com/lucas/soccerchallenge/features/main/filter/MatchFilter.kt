package com.lucas.soccerchallenge.features.main.filter

import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import javax.inject.Inject

class MatchFilter @Inject constructor() {

    private var fullList: List<Match> = emptyList()

    var filteredList: List<Match> = emptyList()
        set(value) {
            field = getFilteredList(value)
        }

    var filters: HashSet<Competition> = hashSetOf(Filters.allFilterCompetition)
        set(value) {
            field = if (value.isEmpty()) hashSetOf(Filters.allFilterCompetition) else value
            filteredList = fullList
        }

    fun setList(list: List<Match>) {
        fullList = list
        filteredList = list
    }

    private fun getFilteredList(list: List<Match>): List<Match> {
        return if (filters.size == 1 && filters.contains(Filters.allFilterCompetition))
            list
        else
            list.filter { filters.contains(it.competition) }
    }
}