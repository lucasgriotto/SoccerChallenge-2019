package com.lucas.soccerchallenge.data.entity

import com.google.gson.annotations.SerializedName
import com.lucas.soccerchallenge.data.Competition
import com.lucas.soccerchallenge.data.Match
import com.lucas.soccerchallenge.data.Score
import com.lucas.soccerchallenge.data.Team
import java.util.*

data class MatchEntity(
    @SerializedName("date")
    val date: Date,
    @SerializedName("venue")
    val venue: VenueEntity,
    @SerializedName("score")
    val score: ScoreEntity? = null,
    @SerializedName("awayTeam")
    val awayTeam: TeamEntity,
    @SerializedName("homeTeam")
    val homeTeam: TeamEntity,
    @SerializedName("id")
    val id: Int,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("competitionStage")
    val competitionStage: CompetitionStageEntity
)

fun MatchEntity.toMatch() = Match(
    id,
    Team(homeTeam.name),
    Team(awayTeam.name),
    date,
    Competition(competitionStage.competition.id, competitionStage.competition.name),
    venue.name,
    state,
    score?.let { Score(it.home, it.away, it.winner) }
)