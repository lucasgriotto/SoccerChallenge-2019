package com.lucas.soccerchallenge.ui.fixture.adapter

import androidx.annotation.ColorRes
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchItemDisplayModel
import com.soccerchallenge.domain.model.Match
import java.util.*

data class FixtureDisplayModel(
    val id: Int,
    override val competitionId: Int,
    val competitionName: String,
    val venueName: String,
    @ColorRes val matchDateColor: Int,
    val teamHomeId: Int,
    val teamHomeName: String,
    val teamAwayId: Int,
    val teamAwayName: String,
    val isPostponed: Boolean,
    override val date: Date
) : MatchItemDisplayModel, MatchDisplayModel

fun Match.toFixtureDisplayModel(): FixtureDisplayModel {
    @ColorRes val matchDateColor = if (isPostponed) {
        R.color.red
    } else {
        R.color.grey
    }
    return FixtureDisplayModel(
        id = id,
        competitionId = competition.id,
        competitionName = competition.name,
        venueName = venueName,
        matchDateColor = matchDateColor,
        teamHomeId = homeTeam.id,
        teamHomeName = homeTeam.name,
        teamAwayId = awayTeam.id,
        teamAwayName = awayTeam.name,
        isPostponed = isPostponed,
        date = date
    )
}