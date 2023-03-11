package com.lucas.soccerchallenge.data.entity

import com.google.gson.annotations.SerializedName

data class CompetitionStageEntity(
    @SerializedName("competition")
    val competition: CompetitionEntity
)