package com.lucas.soccerchallenge.data.entity

import com.google.gson.annotations.SerializedName

data class ScoreEntity(
    @SerializedName("away")
    val away: Int,
    @SerializedName("winner")
    val winner: String? = null,
    @SerializedName("home")
    val home: Int
)
