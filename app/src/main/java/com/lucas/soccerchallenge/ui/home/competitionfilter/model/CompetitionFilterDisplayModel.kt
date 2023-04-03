package com.lucas.soccerchallenge.ui.home.competitionfilter.model

import com.lucas.soccerchallenge.core.model.Competition

data class FilterCompetitionDisplayModel(
    val id: Int,
    val name: String,
    var isSelected: Boolean
)

fun Competition.toFilterCompetitionDisplayModel(isSelected: Boolean) = FilterCompetitionDisplayModel(
    id,
    name,
    isSelected
)

fun FilterCompetitionDisplayModel.toCompetition() = Competition(
    id,
    name
)