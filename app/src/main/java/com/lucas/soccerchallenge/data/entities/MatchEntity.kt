package com.lucas.soccerchallenge.data.entities

import com.google.gson.annotations.SerializedName
import java.util.*

data class MatchEntity(

    @field:SerializedName("date")
    val date: Date,

    @field:SerializedName("venue")
    val venue: VenueEntity,

    @field:SerializedName("score")
    val score: ScoreEntity? = null,

    @field:SerializedName("awayTeam")
    val awayTeam: TeamEntity,

    @field:SerializedName("homeTeam")
    val homeTeam: TeamEntity,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("state")
    val state: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("competitionStage")
    val competitionStage: CompetitionStageEntity
)