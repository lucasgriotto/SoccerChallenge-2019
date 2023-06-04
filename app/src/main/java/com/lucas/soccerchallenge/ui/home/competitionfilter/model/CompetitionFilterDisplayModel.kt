package com.lucas.soccerchallenge.ui.home.competitionfilter.model

data class CompetitionFilterDisplayModel(
    val id: Int,
    val name: String,
    var isSelected: Boolean
)

fun com.soccerchallenge.domain.model.Competition.toFilterCompetitionDisplayModel(isSelected: Boolean) =
    CompetitionFilterDisplayModel(
        id,
        name,
        isSelected
    )