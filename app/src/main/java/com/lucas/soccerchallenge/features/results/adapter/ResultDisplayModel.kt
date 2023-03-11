package com.lucas.soccerchallenge.features.results.adapter

import android.os.Parcelable
import androidx.annotation.ColorRes
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.features.home.match.model.MatchItemDisplayModel
import com.lucas.soccerchallenge.utils.DateUtils
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultDisplayModel(
    val id: Int,
    val competitionName: String,
    val venueName: String,
    val matchDate: String,
    val teamHomeName: String,
    val teamHomeScore: String?,
    val teamAwayName: String,
    val teamAwayScore: String?,
    @ColorRes val scoreHomeColor: Int,
    @ColorRes val scoreAwayColor: Int
) : MatchItemDisplayModel, Parcelable

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
        competitionName = competition.name,
        venueName = venueName,
        matchDate = DateUtils.getUIFormattedDate(date),
        teamHomeName = homeTeam.name,
        teamHomeScore = score?.home.toString(),
        teamAwayName = awayTeam.name,
        teamAwayScore = score?.away.toString(),
        scoreHomeColor = scoreHomeColor,
        scoreAwayColor = scoreAwayColor
    )
}