package com.lucas.soccerchallenge.ui.fixture.adapter

import android.os.Parcelable
import androidx.annotation.ColorRes
import com.lucas.soccerchallenge.R
import com.lucas.soccerchallenge.core.model.Match
import com.lucas.soccerchallenge.ui.home.matchfilter.model.MatchItemDisplayModel
import com.lucas.soccerchallenge.utils.DateUtils
import kotlinx.parcelize.Parcelize

@Parcelize
data class FixtureDisplayModel(
    val id: Int,
    val competitionName: String,
    val venueName: String,
    val matchDate: String,
    @ColorRes val matchDateColor: Int,
    val teamHomeName: String,
    val teamAwayName: String,
    val dayNum: String,
    val dayName: String,
    val isPostponed: Boolean,
) : MatchItemDisplayModel, Parcelable

fun Match.toFixtureDisplayModel(): FixtureDisplayModel {
    @ColorRes val matchDateColor = if (isPostponed) {
        R.color.red
    } else {
        R.color.grey
    }
    return FixtureDisplayModel(
        id = id,
        competitionName = competition.name,
        venueName = venueName,
        matchDate = DateUtils.getUIFormattedDate(date),
        matchDateColor = matchDateColor,
        teamHomeName = homeTeam.name,
        teamAwayName = awayTeam.name,
        dayNum = DateUtils.getMonthDayNumber(date),
        dayName = DateUtils.getWeekDayNameShort(date),
        isPostponed = isPostponed
    )
}