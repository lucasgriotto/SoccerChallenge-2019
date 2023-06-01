package com.lucas.soccerchallenge.ui.home.competitionfilter

import com.lucas.soccerchallenge.core.model.Competition

object CompetitionFilters {

    const val ALL_FILTER_ID = -1
    const val ALL_FILTER_INDEX = 0
    val allFilterCompetition = Competition(ALL_FILTER_ID, "All")

    private val competitionFilterMap = hashMapOf(
        -1 to allFilterCompetition,
        8 to Competition(8, "Premier League"),
        2 to Competition(2, "Carabao Cup"),
        6 to Competition(6, "UEFA Europa League"),
        1 to Competition(1, "FA Cup")
    )

    val competitionFilter = competitionFilterMap.values

    val defaultSelectedCompetition = setOf(allFilterCompetition)

    val defaultSelectedCompetitionIds = defaultSelectedCompetition.map { it.id }.toSet()

    fun getCompetitionsFromIds(ids: Set<Int>): Set<Competition> {
        val competitions = mutableSetOf<Competition>()
        ids.forEach { id ->
            competitionFilterMap[id]?.let { competition ->
                competitions.add(competition)
            }
        }
        return competitions
    }

}
