package com.lucas.soccerchallenge.data.entities

import com.google.gson.annotations.SerializedName

data class ScoreEntity(

    @field:SerializedName("away")
    val away: Int,

    @field:SerializedName("winner")
    val winner: String? = null,

    @field:SerializedName("home")
    val home: Int
)