package com.soccerchallenge.data.network.model

import com.google.gson.annotations.SerializedName

data class ScoreResponse(
        @SerializedName("away")
        val away: Int,
        @SerializedName("winner")
        val winner: String? = null,
        @SerializedName("home")
        val home: Int
)
