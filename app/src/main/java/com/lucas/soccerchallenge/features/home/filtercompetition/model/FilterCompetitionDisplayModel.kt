package com.lucas.soccerchallenge.features.home.filtercompetition.model

import com.lucas.soccerchallenge.data.Competition

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