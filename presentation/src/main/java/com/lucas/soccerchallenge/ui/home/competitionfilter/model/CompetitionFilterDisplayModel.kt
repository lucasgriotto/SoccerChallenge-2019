package com.lucas.soccerchallenge.ui.home.competitionfilter.model

import com.soccerchallenge.domain.model.Competition

data class CompetitionFilterDisplayModel(
        val id: Int,
        val name: String,
        var isSelected: Boolean
)

fun Competition.toFilterCompetitionDisplayModel(isSelected: Boolean) =
        CompetitionFilterDisplayModel(
                id,
                name,
                isSelected
        )