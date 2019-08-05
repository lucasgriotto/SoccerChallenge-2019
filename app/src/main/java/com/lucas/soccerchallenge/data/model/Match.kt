package com.lucas.soccerchallenge.data.model

import java.util.*

data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val date: Date,
    val competition: Competition,
    val venueName: String,
    val state: String?,
    val score: Score?
) {

    fun isPostponed() = state == "postponed"

    fun isHomeWinner() = score?.winner == "home"

    fun isAwayWinner() = score?.winner == "away"
}