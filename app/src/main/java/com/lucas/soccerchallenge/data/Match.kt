package com.lucas.soccerchallenge.data

import java.util.*

data class Match(
    val id: Int,
    val homeTeam: Team,
    val awayTeam: Team,
    val date: Date,
    val competition: Competition,
    val venueName: String,
    val state: String?,
    val score: Score?
) {
    val isPostponed = state == "postponed"

    val isHomeWinner = score?.winner == "home"

    val isAwayWinner = score?.winner == "away"
}