package com.lucas.soccerchallenge.features.filter

import com.lucas.soccerchallenge.data.model.Competition
import com.lucas.soccerchallenge.data.model.Match
import javax.inject.Inject

class MatchFilter @Inject
constructor() {

    private var fullList: List<Match> = emptyList()

    var filteredList: List<Match> = emptyList()
        set(value) {
            field = getFilteredList(value)
        }

    var filters: HashSet<Competition>? = null
        set(value) {
            field = value
            filteredList = fullList
        }

    fun setList(list: List<Match>) {
        fullList = list
        filteredList = list
    }

    private fun getFilteredList(list: List<Match>): List<Match> {
        return filters?.let { fil ->
            list.filter { fil.contains(it.competition) }
        } ?: list
    }
}