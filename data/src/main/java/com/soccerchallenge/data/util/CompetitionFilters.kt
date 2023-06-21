package com.soccerchallenge.data.util

import com.soccerchallenge.domain.model.Competition

object CompetitionFilters {

    const val ALL_FILTER_ID = -1
    const val ALL_FILTER_INDEX = 0
    val allFilterCompetition = Competition(ALL_FILTER_ID, "All")

    val competitionFilter = listOf(
            allFilterCompetition,
            Competition(8, "Premier League"),
            Competition(2, "Carabao Cup"),
            Competition(6, "UEFA Europa League"),
            Competition(1, "FA Cup")
    )

    private val defaultSelectedCompetition = setOf(allFilterCompetition)

    val defaultSelectedCompetitionIds = defaultSelectedCompetition.map { it.id }.toSet()

}
