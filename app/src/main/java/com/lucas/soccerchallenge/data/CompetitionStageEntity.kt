package com.lucas.soccerchallenge.data

import com.google.gson.annotations.SerializedName

data class CompetitionStageEntity(
    @SerializedName("competition")
    val competition: CompetitionEntity
)