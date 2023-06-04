package com.soccerchallenge.data.network.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class MatchResponse(
    @SerializedName("date")
    val date: Date,
    @SerializedName("venue")
    val venue: VenueResponse,
    @SerializedName("score")
    val score: ScoreResponse? = null,
    @SerializedName("awayTeam")
    val awayTeam: TeamResponse,
    @SerializedName("homeTeam")
    val homeTeam: TeamResponse,
    @SerializedName("id")
    val id: Int,
    @SerializedName("state")
    val state: String? = null,
    @SerializedName("type")
    val type: MatchTypeResponse,
    @SerializedName("competitionStage")
    val competitionStage: CompetitionStageResponse
)