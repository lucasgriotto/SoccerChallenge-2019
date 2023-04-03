package com.lucas.soccerchallenge.core.network.model

import com.google.gson.annotations.SerializedName

data class CompetitionStageResponse(
    @SerializedName("competition")
    val competition: CompetitionResponse
)