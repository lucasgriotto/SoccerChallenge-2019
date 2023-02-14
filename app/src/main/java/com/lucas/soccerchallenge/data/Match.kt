package com.lucas.soccerchallenge.data

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Match(
    val id: Int,
    val homeTeam: Team,
    val awayTeam: Team,
    val date: Date,
    val competition: Competition,
    val venueName: String,
    val state: String?,
    val score: Score?
) : Parcelable {
    @IgnoredOnParcel
    val isPostponed = state == "postponed"

    @IgnoredOnParcel
    val isHomeWinner = score?.winner == "home"

    @IgnoredOnParcel
    val isAwayWinner = score?.winner == "away"
}