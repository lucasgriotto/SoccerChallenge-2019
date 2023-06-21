package com.lucas.soccerchallenge.ui.results.adapter

import androidx.annotation.ColorRes
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchDisplayModel
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchItemDisplayModel
import com.soccerchallenge.domain.model.Match
import java.util.Date

data class ResultDisplayModel(
        val id: Int,
        override val competitionId: Int,
        val competitionName: String,
        val venueName: String,
        val teamHomeId: Int,
        val teamHomeName: String,
        val teamHomeScore: String?,
        val teamAwayId: Int,
        val teamAwayName: String,
        val teamAwayScore: String?,
        @ColorRes val scoreHomeColor: Int,
        @ColorRes val scoreAwayColor: Int,
        override val date: Date
) : MatchItemDisplayModel, MatchDisplayModel

fun Match.toResultDisplayModel(): ResultDisplayModel {
    @ColorRes val scoreHomeColor: Int
    @ColorRes val scoreAwayColor: Int
    when {
        isHomeWinner -> {
            scoreHomeColor = R.color.blue
            scoreAwayColor = R.color.darkBlue
        }

        isAwayWinner -> {
            scoreHomeColor = R.color.darkBlue
            scoreAwayColor = R.color.blue
        }

        else -> {
            scoreHomeColor = R.color.darkBlue
            scoreAwayColor = R.color.darkBlue
        }
    }
    return ResultDisplayModel(
            id = id,
            competitionId = competition.id,
            competitionName = competition.name,
            venueName = venueName,
            teamHomeId = homeTeam.id,
            teamHomeName = homeTeam.name,
            teamHomeScore = score?.home.toString(),
            teamAwayId = awayTeam.id,
            teamAwayName = awayTeam.name,
            teamAwayScore = score?.away.toString(),
            scoreHomeColor = scoreHomeColor,
            scoreAwayColor = scoreAwayColor,
            date = date
    )
}