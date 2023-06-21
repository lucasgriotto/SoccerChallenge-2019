package com.soccerchallenge.data.network.model

import com.google.gson.annotations.SerializedName

data class CompetitionStageResponse(
        @SerializedName("competition")
        val competition: CompetitionResponse
)